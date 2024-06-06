package com.example.appecommerce.service;

import com.example.appecommerce.component.HelperClass;
import com.example.appecommerce.entity.Address;
import com.example.appecommerce.entity.BlockedUser;
import com.example.appecommerce.entity.Role;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.BlockUserDto;
import com.example.appecommerce.payload.UserDto;
import com.example.appecommerce.repository.BlockedUserRepository;
import com.example.appecommerce.repository.RoleRepository;
import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HelperClass helperClass;

    @Autowired
    BlockedUserRepository blockedUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse editUser(UUID id, UserDto userDto) {
        //Check if user exists or not
        if (!userRepository.existsById(id)) {

            //return message i it doesn't exist
            return new ApiResponse("This user does not exist", false);
        }

        //Get current user from SecurityContextHolder
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If current user doesn't match to the user of that id then return message
        if (!user.getId().equals(id)) {
            return new ApiResponse("You can't edit some other user", false);
        }

        //Update users info
        user.setFullName(userDto.getFullName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMobileNumber(userDto.getMobileNumber());

        //Get user's address
        Address address = user.getAddress();

        //update user's address
        address.setCity(userDto.getCity());
        address.setDistrict(userDto.getDistrict());
        address.setStreet(userDto.getStreet());

        boolean emailChanged = userDto.isEmailChanged();

        //If email is changed we need to verify it
        if (emailChanged) {
            //Check if this email is blocked or not
            if (blockedUserRepository.existsByEmail(userDto.getEmail())) {
                //Return message if it is
                return new ApiResponse("This email is blocked. Try other emails", false);
            }

            if (userRepository.existsByEmail(userDto.getEmail())){
                return new ApiResponse("This email is currently in the system. Try other emails", false);
            }

            //Generate code
            long randomCode = new Random().nextLong(99999);

            //set emailCode
            String emailCode = randomCode + "";
            user.setEmailCode(emailCode);

            //Send email with link to verify it
            helperClass.changeEmailMessage(userDto.getEmail(), emailCode, id);
        }

        //We don't have to update email if it is not changed

        //Save user to DB
        userRepository.save(user);

        //return message
        return new ApiResponse(emailChanged ? "Your info is updated successfully. In order to change your email please verify it first"
                : "Successfully edited", true);
    }

    @Override
    public ApiResponse deleteUser(UUID id) {
        try {

            //If user doesn't exist tell it to user
            if (!userRepository.existsById(id)) {
                return new ApiResponse("This user does not exists", false);
            }
            //Delete user
            userRepository.deleteById(id);

            //Return message
            return new ApiResponse("Successfully deleted", true);
            //If exception occurs catch it
        } catch (Exception e) {
            //Print stackTrace to analyze
            e.printStackTrace();

            //Tell user that exception occurred
            return new ApiResponse("Problems occurred", false);
        }

    }

    @Override
    public ApiResponse blockUser(BlockUserDto blockedUserDto) {
        try {

            //First we get optional user by its ID
            Optional<User> optionalUser = userRepository.findById(blockedUserDto.getId());

            //If it's empty then we tell it to user
            if (optionalUser.isEmpty()) {
                return new ApiResponse("This user does not exists", false);
            }

            //Getting user from optional
            User user = optionalUser.get();

            //Creating a blockedUser object
            BlockedUser blockedUser = new BlockedUser();

            //Give email and reason why it  is blocked
            blockedUser.setEmail(user.getEmail());
            blockedUser.setReason(blockedUserDto.getReason());

            //Delete this user from system
            userRepository.delete(user);

            //save blockedUser to DB
            blockedUserRepository.save(blockedUser);

            //return message about success
            return new ApiResponse("Successfully deleted and blocked", true);

            //If exception occurs catch it
        } catch (Exception e) {
            //Print stackTrace to analyze
            e.printStackTrace();

            //Tell user that exception occurred
            return new ApiResponse("Problems occurred", false);
        }
    }


    @Override
    public ApiResponse verify(UUID id, String email, String emailCode) {
        try {
            //Get Optional<User> by id
            Optional<User> optionalUser = userRepository.findById(id);

            //If it is empty return message
            if (optionalUser.isEmpty()) {
                return new ApiResponse("User is not found", false);
            }
            //get User from Optional
            User user = optionalUser.get();

            //If email codes doesn't match return message
            if (!user.getEmailCode().equals(emailCode)) {
                return new ApiResponse("Codes doesn't match", false);
            }

            //Set emailCode to null and email to new one
            user.setEmail(email);
            user.setEmailCode(null);

            //Save user to DB
            userRepository.save(user);
            //return message
            return new ApiResponse("Email is successfully changed", false);

            //If exception occurs catch it
        } catch (Exception e) {
            //Print stack trace to analyze it
            e.printStackTrace();

            //Return message
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse unblockUser(String email) {

        try {
            //Get OptionalBlockedUser by email
            Optional<BlockedUser> optionalBlockedUser = blockedUserRepository.findByEmail(email);

            //If optional is empty that means this email is not blocked
            if (optionalBlockedUser.isEmpty()) {
                return new ApiResponse("This email is not blocked", false);
            }

            //Get BlockedUser from optional
            BlockedUser blockedUser = optionalBlockedUser.get();

            //delete blockedUser from DB so that it will not be found in BlockedUser list
            blockedUserRepository.delete(blockedUser);

            //Tell user its success
            return new ApiResponse("Successfully unblocked", true);

            //If exception occurs catch it
        } catch (Exception e) {
            //Print stack trace to analyze it
            e.printStackTrace();

            //Return message
            return new ApiResponse("Problems occurred", false);
        }
    }


    @Override
    public HttpEntity<?> getBlockedUsers(Integer pageNumber, Integer pageSize) {

        //Get Pageable object
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Receive Page of BlockedUsers from DB
        Page<BlockedUser> blockedUsers = blockedUserRepository.findAll(pageable);

        //Return message
        return new ResponseEntity<>(blockedUsers, HttpStatus.OK);
    }

    @Override
    public ApiResponse addRoleToUser(UUID userId, UUID roleId) {
        try {

            //Get optional User by id
            Optional<User> optionalUser = userRepository.findById(userId);

            //If optionalUser is empty, tell it to user
            if (optionalUser.isEmpty()) {
                return new ApiResponse("The User is not found", false);
            }

            //Get optionalRole from DB
            Optional<Role> optionalRole = roleRepository.findById(roleId);

            //If optionalRole is empty tell it to user
            if (optionalRole.isEmpty()) {
                return new ApiResponse("Role with this id does not exists", false);
            }

            //Get role from optionalRole
            Role role = optionalRole.get();

            //Get user from optionalUser
            User user = optionalUser.get();

            //Set role of the user
            user.setRole(role);

            //Save user to DB
            userRepository.save(user);

            //Return message about success
            return new ApiResponse("Successfully added", true);

            //If exception occurs
        } catch (Exception e) {

            //print stackTrace to analyze later
            e.printStackTrace();

            //return message about problems
            return new ApiResponse("Problems occurred", false);
        }
    }
}

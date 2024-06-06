package com.example.appecommerce.service;

import com.example.appecommerce.component.HelperClass;
import com.example.appecommerce.entity.Address;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.LoginDto;
import com.example.appecommerce.payload.RegisterDto;
import com.example.appecommerce.repository.BlockedUserRepository;
import com.example.appecommerce.repository.RoleRepository;
import com.example.appecommerce.repository.UserRepository;
import com.example.appecommerce.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    HelperClass helperClass;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BlockedUserRepository blockedUserRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Override
    public ApiResponse registerUser(RegisterDto registerDto) {
        try {
            //If this email is blocked tell it to user
            if (blockedUserRepository.existsByEmail(registerDto.getEmail())) {
                return new ApiResponse("This email is blocked from system", false);
            }

            //If this email is already register by another user tell it to user
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                return new ApiResponse("This email is already registered!!! Please choose other email", false);
            }

            //Create user and set necessary info
            User user = new User(
                    registerDto.getFullName(),
                    registerDto.getEmail(),
                    passwordEncoder.encode(registerDto.getPassword()),
                    roleRepository.findByName("User").orElseThrow(ResourceNotFoundException::new),
                    registerDto.getMobileNumber(),
                    new Address(
                            registerDto.getCity(),
                            registerDto.getDistrict(),
                            registerDto.getStreet()
                            )
            );

            //Generate emailCode and set it to user
            String emailCode = UUID.randomUUID().toString();
            user.setEmailCode(emailCode);

            //Check if email is sent successfully
            if (helperClass.verifyEmail(user.getEmail(), emailCode)) {
                //if email is successfully send, save user to DB but don't enable until verifies
                userRepository.save(user);
                return new ApiResponse("Successfully registered please verify your email", true);
            } else {
                //if email can't be sent tell it to user
                return new ApiResponse("Can't send a message to this email", false);
            }
            //If exception occurs print stackTrace and tell user that exception occurred
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse verifyEmail(String email, String emailCode) {
        try {
            //Get optionalUser by its username
            Optional<User> optionalUser = userRepository.findByEmail(email);

            //If optional user is empty tell user
            if (optionalUser.isEmpty()) {
                return new ApiResponse("User is not found", false);
            }

            //Get user from optional
            User user = optionalUser.get();

            //check if email codes are match
            if (emailCode.equals(user.getEmailCode())) {

                //Set email code to null so we don't re-enable again
                user.setEmailCode(null);
                //Enable user
                user.setEnabled(true);

                //save user to DB
                userRepository.save(user);
                return new ApiResponse("Successfully Verified", true);
            }
            return new ApiResponse("This user is verified before", false);

            //If exception occurs print stackTrace and tell user that exception occurred
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error occurred", false);
        }
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        try {

            //Get optionalUser by email
            Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

            //If optional user is empty tell user
            if (optionalUser.isEmpty()) {
                return new ApiResponse("User does not exist", false);
            }

            //Get user from optional
            User user = optionalUser.get();

            //authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            //generate token
            String token = jwtProvider.generateToken(user.getUsername());

            return new ApiResponse(token, true);

            //If exception occurs print stackTrace and tell user that exception occurred
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Problems occurred", false);
        }
    }


}

package com.example.appecommerce.service;

import com.example.appecommerce.component.HelperClass;
import com.example.appecommerce.entity.Role;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.RoleDto;
import com.example.appecommerce.repository.RoleRepository;
import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    HelperClass helperClass;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addRole(RoleDto roleDto) {
        try {
            //If role names are the same we return error message
            if (roleRepository.existsByName(roleDto.getName())) {
                return new ApiResponse("There is a role with this name in the system", false);
            }

            //Creating a role and setting its info
            Role role = new Role(
                    roleDto.getName(),
                    roleDto.getPermissions(),
                    roleDto.getDescription()
            );

            //Saving new role to DB
            roleRepository.save(role);
            //Returning a message about successful process
            return new ApiResponse("Successfully saved", true);
        } catch (Exception e) {
            //If exception occurs we print it to console and send user a message
            e.printStackTrace();
            return new ApiResponse("Problems occurred while saving the role", false);
        }
    }

    @Override
    public ApiResponse editRole(UUID id, RoleDto roleDto) {
        try {
            //Get the optional role from DB
            Optional<Role> optionalRole = roleRepository.findById(id);

            //If optionalRole is empty then return message
            if (optionalRole.isEmpty()) {
                return new ApiResponse("This role does not exist", false);
            }
            //Get role from optionalRole
            Role role = optionalRole.get();

            //Setting new info to role
            role.setName(roleDto.getName());
            role.setDescription(roleDto.getDescription());
            role.setPermissions(roleDto.getPermissions());

            //Saving it to DB
            roleRepository.save(role);

            //Return message about success
            return new ApiResponse("Successfully edited", true);
        } catch (Exception e) {
            //If exception occurs then print it to console
            e.printStackTrace();
            //Return message that error occurred
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse deleteRole(UUID id) {
        try {
            //If this role does not exist then warn the user
            if (!roleRepository.existsById(id)) {
                return new ApiResponse("This role does not exist", false);
            }
            //Check if there's a user with this role
            if (userRepository.existsByRoleId(id)) {
                return new ApiResponse("There is a user with this role", false);
            }
            //delete the role
            roleRepository.deleteById(id);
            //Send message about success
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            //Print exceptions message to console to analyze it
            e.printStackTrace();
            //Tell user that there is some problems
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public HttpEntity<?> getRole(UUID id) {
        try {
            //Getting role from DB by its id
            Optional<Role> optionalRole = roleRepository.findById(id);

            //If it is empty tell user that role is not found
            if (optionalRole.isEmpty()) {
                return new ResponseEntity<>("Role is not found", HttpStatus.CONFLICT);
            }
            //Getting role from optional
            Role role = optionalRole.get();

            //Map Role to RoleDto to avoid sending unnecessary info
            RoleDto roleDto = helperClass.mapRoleToRoleDto(role);

            //Return the roleDto
            return ResponseEntity.ok(roleDto);
        } catch (Exception e){
            //If exception occurs return user its message with status CONFLICT
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @Override
    public HttpEntity<?> getRoles() {
        //Get all Roles and map them to RoleDto
        List<RoleDto> roleDtoList = roleRepository.findAll().stream().map(helperClass::mapRoleToRoleDto).toList();

        return ResponseEntity.ok(roleDtoList);
    }
}

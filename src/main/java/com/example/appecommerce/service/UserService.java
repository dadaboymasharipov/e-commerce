package com.example.appecommerce.service;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.BlockUserDto;
import com.example.appecommerce.payload.UserDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface UserService {

    /**
     * This method helps to edit Users info
     *
     * @param id      ID of the user to be edited
     * @param userDto new info about user
     * @return ApiResponse with message and success
     */
    ApiResponse editUser(UUID id, UserDto userDto);

    /**
     * This method deletes User from DB
     *
     * @param id ID of the user to be deleted
     * @return ApiResponse with message and succes
     */
    ApiResponse deleteUser(UUID id);

    /**
     * This method blocks email from system
     *
     * @param blockedUser brings info about the user to be blocked
     * @return ApiResponse with message and success
     */
    ApiResponse blockUser(BlockUserDto blockedUser);

    /**
     * This method verifies email if user wants to change his email
     *
     * @param id        ID of the user
     * @param email     new email of that user
     * @param emailCode email code that has been sent by system
     * @return ApiResponse with message and success
     */
    ApiResponse verify(UUID id, String email, String emailCode);

    /**
     * This method helps to unblock a particular email
     *
     * @param email the email that should be unblocked
     * @return ApiResponse with message and success
     */
    ApiResponse unblockUser(String email);

    /**
     * This method returns all blocked emails' list
     *
     * @param pageNumber which page is requested
     * @param pageSize   how many objects should be in the list
     * @return Page of BlockedUsers
     */
    HttpEntity<?> getBlockedUsers(Integer pageNumber, Integer pageSize);

    /**
     * This method gives user a role
     *
     * @param userId which user should get the role
     * @param roleId which role has to be added
     * @return ApiResponse with message and success
     */
    ApiResponse addRoleToUser(UUID userId, UUID roleId);
}

package com.example.appecommerce.service;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.RoleDto;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface RoleService {
    /**
     * This method adds Role to DB
     *
     * @param roleDto brings info about new role
     * @return ApiResponse with message and success
     */
    ApiResponse addRole(RoleDto roleDto);

    /**
     * THis method helps to edit a particular role
     *
     * @param id      id of the role
     * @param roleDto brings info about new state of the role
     * @return ApiResponse with message and success
     */
    ApiResponse editRole(UUID id, RoleDto roleDto);

    /**
     * This method helps to delete role from DB
     *
     * @param id id of a role
     * @return ApiResponse with message and success
     */
    ApiResponse deleteRole(UUID id);

    /**
     * This method return a particular role
     *
     * @param id id of the role
     * @return ApiResponse with message and success
     */
    HttpEntity<?> getRole(UUID id);

    /**
     * This method returns all existing roles of system
     *
     * @return ApiResponse with message and success
     */
    HttpEntity<?> getRoles();
}

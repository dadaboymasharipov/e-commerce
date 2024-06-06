package com.example.appecommerce.service;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.LoginDto;
import com.example.appecommerce.payload.RegisterDto;

/**
 * Our AuthService interface to predefine necessary methods
 */
public interface AuthService {

    /**
     * This method helps to save user to DB
     * @param registerDto brings necessary info about user
     * @return ApiResponse with success and message
     */
    ApiResponse registerUser(RegisterDto registerDto);

    /**
     * This method helps to verify user's email
     * @param email email of the user
     * @param emailCode email code of the user
     * @return ApiResponse with success and message
     */
    ApiResponse verifyEmail(String email, String emailCode);

    /**
     * This method helps user to login and returns token if successfull
     * @param loginDto brings info of the user
     * @return ApiResponse with success and message or token
     */
    ApiResponse login(LoginDto loginDto);

}

package com.example.appecommerce.service;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.CategoryDto;

import java.util.UUID;

public interface CategoryService {

    /**
     * This method adds category to the DB
     *
     * @param categoryDto dto that we get info of the category
     * @return ApiResponse with message and success
     */
    ApiResponse addCategory(CategoryDto categoryDto);

    /**
     * This method edits the category info
     * @param id          id of the category
     * @param categoryDto dto that we get updated info
     * @return ApiResponse with message and success
     */
    ApiResponse editCategory(UUID id, CategoryDto categoryDto);

    /**
     * This method deletes the category
     * @param id id of the category to be deleted
     * @return ApiResponse with message and success
     */
    ApiResponse deleteCategory(UUID id);
}

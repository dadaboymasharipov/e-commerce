package com.example.appecommerce.controller;

import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.CategoryDto;
import com.example.appecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize(value = "hasAuthority('CREATE_CATEGORY')")
    public HttpEntity<?> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize(value = "hasAuthority('EDIT_CATEGORY')")
    public HttpEntity<?> editCategory(@Valid @RequestBody CategoryDto categoryDto,
                                      @PathVariable UUID id) {
        ApiResponse apiResponse = categoryService.editCategory(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(value = "hasAuthority('DELETE_CATEGORY')")
    public HttpEntity<?> deleteCategory(@PathVariable UUID id) {
        ApiResponse apiResponse = categoryService.deleteCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

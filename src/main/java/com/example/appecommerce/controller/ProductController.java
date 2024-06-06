package com.example.appecommerce.controller;

import com.example.appecommerce.aop.CurrentUser;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping(value = "/add")
    public HttpEntity<?> addProduct(@CurrentUser User user,
                                    @RequestPart("file") MultipartFile multipartFile,
                                    @Valid @RequestPart("info") ProductDto productDto) throws IOException {
        ApiResponse apiResponse = productService.addProduct(user, productDto, multipartFile);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/edit/{id}")
    public HttpEntity<?> editProduct(@Valid @RequestPart("info") ProductDto productDto,
                                     @PathVariable UUID id,
                                     @CurrentUser User user,
                                     @RequestPart("file") MultipartFile multipartFile) throws IOException {
        ApiResponse apiResponse = productService.editProduct(id, user, productDto, multipartFile);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_MY_PRODUCT')")
    @DeleteMapping("/deleteMy/{id}")
    public HttpEntity<?> deleteMyProduct(@PathVariable UUID id,
                                         @CurrentUser User user) {
        ApiResponse apiResponse = productService.deleteMyProduct(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PreAuthorize(value = "hasAuthority('DELETE_ANY_PRODUCT')")
    @DeleteMapping("/deleteAny/{id}")
    public HttpEntity<?> deleteAnyProduct(@PathVariable UUID id) {
        ApiResponse apiResponse = productService.deleteAnyProduct(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}

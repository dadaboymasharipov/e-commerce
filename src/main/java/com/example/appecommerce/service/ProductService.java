package com.example.appecommerce.service;

import com.example.appecommerce.entity.User;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ProductService {

    /**
     * This method adds product to DB
     *
     * @param user          current user of the system
     * @param productDto    brings info about product
     * @param multipartFile brings image of the product
     * @return ApiResponse with message and success
     * @throws IOException
     */
    ApiResponse addProduct(User user, ProductDto productDto, MultipartFile multipartFile) throws IOException;

    /**
     * This method deletes user's product
     *
     * @param id   id of the product
     * @param user current user of the system
     * @return ApiResponse with message and success
     */
    ApiResponse deleteMyProduct(UUID id, User user);

    /**
     * This method helps to delete any product from the system in case its restricted
     *
     * @param id id of the product
     * @return ApiResponse with message and success
     */
    ApiResponse deleteAnyProduct(UUID id);

    /**
     * This method helps to edit product's info or image
     *
     * @param id            id of the product
     * @param user          current user of the system
     * @param productDto    brings info about product
     * @param multipartFile brings image of the product
     * @return ApiResponse with message and success
     * @throws IOException
     */
    ApiResponse editProduct(UUID id, User user, ProductDto productDto, MultipartFile multipartFile) throws IOException;

}

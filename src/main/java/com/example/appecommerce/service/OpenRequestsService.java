package com.example.appecommerce.service;

import com.example.appecommerce.payload.MappedUser;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.payload.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;

import java.util.UUID;

public interface OpenRequestsService {

    /**
     * Method to get a category
     *
     * @param id id of the category
     * @return CategoryDto with id, name, number of products and created date in long
     */
    HttpEntity<?> getCategory(UUID id);

    /**
     * Method to get all categories
     *
     * @return List of CategoryDto objects
     */
    HttpEntity<?> getCategories();

    /**
     * This method return a product
     * @param id id of the product
     * @return ProductDto object with necessary info
     */
    HttpEntity<?> getProduct(UUID id);

    /**
     * This method returns all products page by page
     * @param pageNum indicates which page to return
     * @param pageSize how many ProductDto objects will be in one page
     * @return Pages of ProductDto
     */
    Page<ProductDto> getProducts(int pageNum, int pageSize);

    /**
     * This method returns all products of a particular owner
     * @param id ownerId
     * @param pageNumber Which page it is
     * @param pageSize how many elements should be in one page
     * @return Page which contains 10 product
     */
    Page<ProductDto> getProductsByOwner(UUID id, Integer pageNumber, Integer pageSize);

    /**
     * This method returns all products that belongs to a certain category by page
     * @param id id of the category
     * @param pageNumber which page should be returned
     * @param pageSize how many products should be in that page
     * @return Page of products
     */
    Page<ProductDto>  getProductsByCategory(UUID id, Integer pageNumber, Integer pageSize);

    /**
     * This method returns us a particular User
     * @param id id of the User
     * @return UserDto with only necessary info
     */
    HttpEntity<?> getUser(UUID id);

    /**
     * This method returns us all the users of the system
     * @param pageNumber which page has to be returned
     * @param pageSize how many elements should be in a page
     * @return All Users of the system by Page
     */
    Page<MappedUser> getUsers(Integer pageNumber, Integer pageSize);
}

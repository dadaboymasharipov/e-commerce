package com.example.appecommerce.controller;

import com.example.appecommerce.payload.MappedUser;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.payload.UserDto;
import com.example.appecommerce.service.OpenRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * This class consists only open sources like view products and categories
 */
@RestController
@RequestMapping("/api/open")
public class OpenRequestsController {

    @Autowired
    OpenRequestsService openRequestsService;

    @GetMapping("/getCategory/{id}")
    public HttpEntity<?> getCategory(@PathVariable UUID id) {
        return openRequestsService.getCategory(id);
    }

    @GetMapping("/getCategories")
    public HttpEntity<?> getCategories() {
        return openRequestsService.getCategories();
    }

    @GetMapping("/getProduct/{id}")
    public HttpEntity<?> getProduct(@PathVariable UUID id){
        return openRequestsService.getProduct(id);
    }


    @GetMapping("/getProducts")
    public Page<ProductDto> getProducts(@RequestParam("pageNumber") Integer pageNumber,
                                        @RequestParam("pageSize") Integer pageSize){
        return openRequestsService.getProducts(pageNumber-1, pageSize);
    }


    @GetMapping("/getProductsByOwner/{id}")
    public Page<ProductDto> getProductsByOwner(@PathVariable UUID id,
                                               @RequestParam("pageNumber") Integer pageNumber,
                                               @RequestParam("pageSize") Integer pageSize){
        return openRequestsService.getProductsByOwner(id, pageNumber-1, pageSize);
    }

    @GetMapping("/getProductsByCategory/{id}")
    public Page<ProductDto> getProductsByCategory(@PathVariable UUID id,
                                               @RequestParam("pageNumber") Integer pageNumber,
                                               @RequestParam("pageSize") Integer pageSize){
        return openRequestsService.getProductsByCategory(id, pageNumber-1, pageSize);
    }

    @GetMapping("/getUser/{id}")
    public HttpEntity<?> getUser(@PathVariable UUID id) {
        return openRequestsService.getUser(id);
    }

    @GetMapping("/getUsers")
    public Page<MappedUser> getUsers(@RequestParam("pageNumber") Integer pageNumber,
                                     @RequestParam("pageSize") Integer pageSize) {
        return openRequestsService.getUsers(pageNumber-1, pageSize);
    }


}

package com.example.appecommerce.controller;

import com.example.appecommerce.service.OpenRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HttpEntity<?> getProducts(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return openRequestsService.getProducts(pageNumber, pageSize);
    }


    @GetMapping("/getProductsByOwner/{id}")
    public HttpEntity<?> getProductsByOwner(@PathVariable UUID id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return openRequestsService.getProductsByOwner(id, pageNumber, pageSize);
    }

    @GetMapping("/getProductsByCategory/{id}")
    public HttpEntity<?> getProductsByCategory(@PathVariable UUID id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return openRequestsService.getProductsByCategory(id, pageNumber, pageSize);
    }

    @GetMapping("/getUser/{id}")
    public HttpEntity<?> getUser(@PathVariable UUID id) {
        return openRequestsService.getUser(id);
    }

    @GetMapping("/getUsers")
    public HttpEntity<?> getUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return openRequestsService.getUsers(pageNumber, pageSize);
    }


}

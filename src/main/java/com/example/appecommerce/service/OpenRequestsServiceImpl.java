package com.example.appecommerce.service;

import com.example.appecommerce.component.HelperClass;
import com.example.appecommerce.entity.Category;
import com.example.appecommerce.entity.Product;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.exceptions.CategoryNotFoundException;
import com.example.appecommerce.exceptions.ProductNotFoundException;
import com.example.appecommerce.payload.CategoryDto;
import com.example.appecommerce.payload.MappedUser;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.repository.CategoryRepository;
import com.example.appecommerce.repository.ProductRepository;
import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OpenRequestsServiceImpl implements OpenRequestsService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    HelperClass helperClass;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public HttpEntity<?> getCategory(UUID id) {
        //Getting a category from DB by its id
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        //Converting it to CategoryDto
        CategoryDto categoryDto = helperClass.mapCategoryToCategoryDto(category);
        return ResponseEntity.ok(categoryDto);
    }

    @Override
    public HttpEntity<?> getCategories() {
        //Getting all categories from DB
        List<Category> categories = categoryRepository.findAll();

        //Converting them to CategoryDto and saving as a List
        List<CategoryDto> categoryDtoList = categories.stream().map(
                category -> helperClass.mapCategoryToCategoryDto(category)).collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtoList);
    }

    @Override
    public HttpEntity<?> getProduct(UUID id) {
        //Getting products from DB by its ID
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        //Converting to ProductDto so only necessary info wil be sent
        ProductDto productDto = helperClass.mapProductToProductDto(product);

        return ResponseEntity.ok(productDto);
    }


    @Override
    public Page<ProductDto> getProducts(int pageNum, int pageSize) {
        //We create a pageable to define how many objects and which page is necessary
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());

        //Get particular page of objects
        Page<Product> productsByPage = productRepository.findAll(pageable);

        //Convert Products to ProductDto List so unnecessary info doesn't send to user
        List<ProductDto> productDtoList = productsByPage.stream().map(helperClass::mapProductToProductDto).collect(Collectors.toList());

        //Return Page of products
        return new PageImpl<>(productDtoList, pageable, productsByPage.getTotalElements());
    }

    @Override
    public Page<ProductDto> getProductsByOwner(UUID id, Integer pageNumber, Integer pageSize) {
        //Create a Pageable to define how many objects and which page Should be sent to User
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Get all Products of a certain user
        Page<Product> productPage = productRepository.findAllByOwnerId(id, pageable);

        //Convert Products to ProductDto List so unnecessary info wil not be sent
        List<ProductDto> products = productPage.stream().map(helperClass::mapProductToProductDto).toList();

        return new PageImpl<>(products, pageable, productPage.getTotalElements());
    }


    @Override
    public Page<ProductDto> getProductsByCategory(UUID id, Integer pageNumber, Integer pageSize) {
        //Getting category from DB
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        //Getting products which belongs to this category
        List<Product> products = productRepository.findAllByCategoryName(category.getName());

        //Converting products to ProductDto List so unnecessary info doesn't go to user
        List<ProductDto> productDtoList = products.stream().map(helperClass::mapProductToProductDto).toList();

        //Creating a Pageable object
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return new PageImpl<>(productDtoList, pageable, productDtoList.size());
    }

    @Override
    public HttpEntity<?> getUser(UUID id) {
        try {
            //Get user by its ID
            Optional<User> optionalUser = userRepository.findById(id);
            //If optional is false return message
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("This user does not exist", HttpStatus.CONFLICT);
            }

            //Get User from optional
            User user = optionalUser.get();

            //Map User to MappedUser so only necessary info goes to the user
            MappedUser mappedUser = helperClass.mapUserToMappedUser(user);

            //return User
            return new ResponseEntity<>(mappedUser, HttpStatus.OK);

            //If exception occurs catch it
        } catch (Exception e) {
            //Print its stackTrace, so we can analyze what is the exception
            e.printStackTrace();

            //Tell User that exception occurred
            return new ResponseEntity<>("Problems occurred", HttpStatus.CONFLICT);
        }
    }

    @Override
    public Page<MappedUser> getUsers(Integer pageNumber, Integer pageSize) {
        //Get Pageable with pageNumber and pageSize
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Get all users from DB
        List<MappedUser> allMappedUsers = userRepository.findAll().stream().map(helperClass::mapUserToMappedUser).toList();

        //Page them
        return new PageImpl<>(allMappedUsers, pageable, allMappedUsers.size());

    }
}
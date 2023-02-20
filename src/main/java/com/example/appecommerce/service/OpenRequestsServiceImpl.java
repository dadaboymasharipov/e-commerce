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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public HttpEntity<?> getProducts(int pageNum, int pageSize) {
        //We create a pageable to define how many objects and which page is necessary
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        //Convert Products to ProductDto List so unnecessary info doesn't send to user
        List<ProductDto> productDtoList = productRepository.findAll().stream().map(helperClass::mapProductToProductDto).collect(Collectors.toList());

        //Create a Page of products
        Page<ProductDto> products = new PageImpl<>(productDtoList, pageable, productDtoList.size());

        return ResponseEntity.ok(products);
    }

    @Override
    public HttpEntity<?> getProductsByOwner(UUID id, Integer pageNumber, Integer pageSize) {
        //Get all Products of a certain user
        List<Product> allByOwnerId = productRepository.findAllByOwnerId(id);

        //Convert Products to ProductDto List so unnecessary info wil not be sent
        List<ProductDto> products = allByOwnerId.stream().map(helperClass::mapProductToProductDto).toList();

        //Create a Pageable to define how many objects and which page Should be sent to User
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        //Create a Page of ProductsDto
        Page<ProductDto> productDtos = new PageImpl<>(products, pageable, products.size());

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }


    @Override
    public HttpEntity<?> getProductsByCategory(UUID id, Integer pageNumber, Integer pageSize) {
        //Getting category from DB
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));

        //Getting products which belongs to this category
        List<Product> products = productRepository.findAllByCategoryName(category.getName());

        //Converting products to ProductDto List so unnecessary info doesn't go to user
        List<ProductDto> productDtoList = products.stream().map(helperClass::mapProductToProductDto).toList();

        //Creating a Pageable object
        Pageable pageable = PageRequest.of(pageNumber, 20);

        //Saving products to Pages
        Page<ProductDto> productDtos = new PageImpl<>(productDtoList, pageable, productDtoList.size());

        return ResponseEntity.ok(productDtos);
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
        } catch (Exception e){
            //Print its stackTrace, so we can analyze what is the exception
            e.printStackTrace();

            //Tell User that exception occurred
            return new ResponseEntity<>("Problems occurred", HttpStatus.CONFLICT);
        }
    }

    @Override
    public HttpEntity<?> getUsers(Integer pageNumber, Integer pageSize) {
        try {
            //Get Pageable with pageNumber and pageSize
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            //Get all users from DB
            List<MappedUser> allMappedUsers = userRepository.findAll().stream().map(helperClass::mapUserToMappedUser).toList();

            //Page them
            Page<MappedUser> mappedUsers = new PageImpl<>(allMappedUsers, pageable, allMappedUsers.size());

            //Return appropriate page
            return new ResponseEntity<>(mappedUsers, HttpStatus.OK);

            //If exception occurs catch it
        } catch (Exception e){
            //Print stackTrace to analyze
            e.printStackTrace();

            //Tell user that exception occurred
            return new ResponseEntity<>("Problems occurred", HttpStatus.CONFLICT);
        }
    }



}
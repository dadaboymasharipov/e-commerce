package com.example.appecommerce.service;

import com.example.appecommerce.entity.Attachment;
import com.example.appecommerce.entity.Category;
import com.example.appecommerce.entity.Product;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.exceptions.CategoryNotFoundException;
import com.example.appecommerce.exceptions.ProductNotFoundException;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.repository.AttachmentRepository;
import com.example.appecommerce.repository.CategoryRepository;
import com.example.appecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional()
    @Override
    public ApiResponse addProduct(User user, ProductDto productDto, MultipartFile multipartFile) throws IOException {

        //Check if the has a product with this name
        if (productRepository.existsByNameAndOwner(productDto.getName(), user)) {
            return new ApiResponse("You already have a product with this name", false);
        }

        //If image of the product is not uploaded return message
        if (multipartFile == null) {
            return new ApiResponse("Image cannot be null", false);
        }

        //Create attachment for image of the product and set properties
        Attachment attachment = new Attachment(
                multipartFile.getOriginalFilename(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes()
        );

        //Get category which product belongs
        UUID categoryId = productDto.getCategoryId();

        //Find category by its id if not found, return exception
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));

        //Create product and set properties
        Product product = new Product(
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                category,
                user,
                attachment
        );

        //save product to DB
        productRepository.save(product);

        //return message
        return new ApiResponse("Successfully added", true);

    }

    @Override
    public ApiResponse deleteMyProduct(UUID id, User user) {

        //Get product from DB or throw exception if it is not found
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        //CHeck if current user owns this product
        if (!product.getOwner().equals(user)) {
            //return message
            return new ApiResponse("You can't delete someone else's product", false);
        }

        //delete from DB
        productRepository.delete(product);
        return new ApiResponse("Successfully deleted", true);
    }

    @Override
    public ApiResponse deleteAnyProduct(UUID id) {
        try {
            //If this product doesn't exist in the DB return message
            if (!productRepository.existsById(id)) {
                return new ApiResponse("This product does not exists", false);
            }

            //Delete from DB
            productRepository.deleteById(id);

            return new ApiResponse("Successfully deleted", true);

            //If exception occurs
        } catch (Exception e) {
            //print stackTrace to analyze it later
            e.printStackTrace();

            //return message
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse editProduct(UUID id, User user, ProductDto productDto, MultipartFile multipartFile) throws IOException {
        try {

            //Get product from DB or throw exception
            Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

            //If current User is not owner tell it to user
            if (!product.getOwner().equals(user)) {
                return new ApiResponse("You can't edit this product", false);
            }

            //Get Attachment from Product
            Attachment attachment1 = product.getAttachment();

            //Set its properties
            attachment1.setContentType(multipartFile.getContentType());
            attachment1.setName(multipartFile.getOriginalFilename());
            attachment1.setSize(multipartFile.getSize());
            attachment1.setContent(multipartFile.getBytes());

            //Get category id from productDto
            UUID categoryId = productDto.getCategoryId();

            //Change properties of the product
            product.setName(product.getName());
            product.setCategory(
                    categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId))
            );
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            //SAve it to DB
            productRepository.save(product);
            return new ApiResponse("Successfully edited", true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

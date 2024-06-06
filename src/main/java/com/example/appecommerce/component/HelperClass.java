package com.example.appecommerce.component;

import com.example.appecommerce.entity.Category;
import com.example.appecommerce.entity.Product;
import com.example.appecommerce.entity.Role;
import com.example.appecommerce.entity.User;
import com.example.appecommerce.payload.CategoryDto;
import com.example.appecommerce.payload.MappedUser;
import com.example.appecommerce.payload.ProductDto;
import com.example.appecommerce.payload.RoleDto;
import com.example.appecommerce.repository.ProductRepository;
import com.example.appecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * This class contains all methods that is necessary to others
 */
@Component
public class HelperClass {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * This method sends link to user so they can verify
     *
     * @param email     Email of the user
     * @param emailCode user's emailCode
     * @return true if everything is successful
     */
    public boolean verifyEmail(String email, String emailCode) {
        try {
            //Create SimpleMailMessage object
            SimpleMailMessage message = new SimpleMailMessage();
            //Set from whose name the message should be sent
            message.setFrom("noreply@gmail.com");
            //Set who is the receiver
            message.setTo(email);
            //Set message's subject
            message.setSubject("Verify your email");
            //Set message's context
            message.setText("follow this link: http://localhost:8080/api/auth/verifyEmail?email=" + email + "&emailCode=" + emailCode);
            //send the email
            javaMailSender.send(message);
            return true;

            //If exception occurs print stack trace and return false
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method maps Category to CategoryDto
     *
     * @param category category that is being mapped
     * @return CategoryDto object
     */
    public CategoryDto mapCategoryToCategoryDto(Category category) {
        //Create CategoryDto object
        CategoryDto categoryDto = new CategoryDto();

        //Set necessary properties
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setCreateAt(category.getCreatedAt());

        //Find how many products have this category
        int size = productRepository.findAllByCategoryName(category.getName()).size();

        //Set number of products
        categoryDto.setNumberOfProducts(size);

        return categoryDto;
    }

    /**
     * This method maps Product to ProductDto
     *
     * @param product product that is being mapped
     * @return ProductDto object with mapped info
     */
    public ProductDto mapProductToProductDto(Product product) {
        //Create ProductDto object
        ProductDto productDto = new ProductDto();

        //Set necessary properties and info
        productDto.setName(product.getName());
        productDto.setPicture(product.getAttachment().getContent());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setOwnerName(product.getOwner().getFullName());
        productDto.setCategoryName(product.getCategory().getName());
        productDto.setAddedDate(product.getCreatedAt());

        return productDto;
    }

    /**
     * This method maps Role to RoleDto
     *
     * @param role role that is being mapped
     * @return RoleDto object
     */
    public RoleDto mapRoleToRoleDto(Role role) {
        //If role's createdBy is null then return owner's username
        String createdByEmail = "dadaboymasharipov512@gmail.com";

        if (role.getCreatedBy() != null) {
            //If role is created by someone else then return its username
            createdByEmail = userRepository.findById(role.getCreatedBy()).orElse(null).getEmail();
        }


        //Creating RoleDto and giving its properties
        RoleDto roleDto = new RoleDto(
                role.getName(),
                role.getDescription(),
                role.getPermissions(),
                role.getCreatedAt(),
                createdByEmail
        );

        //return RoleDto
        return roleDto;
    }

    /**
     * This method is sent when user wants to edit its email
     *
     * @param email     new email that has to be set to user
     * @param emailCode necessary email code
     * @param id        id of the user who is editing his profile
     */
    public void changeEmailMessage(String email, String emailCode, UUID id) {

        //Create SimpleMailMessage
        SimpleMailMessage message = new SimpleMailMessage();

        //Set necessary info
        message.setFrom("noreply@gmail.com");
        message.setTo(email);
        message.setSubject("Email Verification");
        message.setText("Follow this: http://localhost:8080/api/user/verify?id=" + id + "&email=" + email + "&emailCode=" + emailCode);

        //Send the message
        javaMailSender.send(message);
    }

    /**
     * This method maps User to MappedUser so unnecessary or private info will not go to viewer
     *
     * @param user which user should be mapped
     * @return MappedUser object
     */
    public MappedUser mapUserToMappedUser(User user) {

        //Create MappedUser object and set its properties
        MappedUser mappedUser = new MappedUser(
                user.getFullName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getAddress().getCity(),
                user.getAddress().getDistrict(),
                user.getAddress().getStreet()
        );
        return mappedUser;
    }
}

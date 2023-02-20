package com.example.appecommerce.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotNull(message = "Name can't be empty")
    @NotBlank(message = "Name can't be blank")
    private String name;

    @Length(max = 1000)
    @NotNull(message = "Please add small description about your product!!!")
    private String description;


    @NotNull(message = "Price can't be empty")
    private Double price;//Evaluated in the $

    @NotNull(message = "You should choose one of the categories")
    private UUID categoryId;

    private String categoryName;

    private String ownerName;

    private byte[] picture;
    private Date addedDate;
}

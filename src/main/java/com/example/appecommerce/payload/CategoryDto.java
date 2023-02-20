package com.example.appecommerce.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private UUID id;//ID of the category

    @NotNull(message = "Category name can't be empty")
    private String name;//Name of the category

    private int numberOfProducts;//How many products have

    private Date createAt;//When this category is created
}

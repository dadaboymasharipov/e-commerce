package com.example.appecommerce.service;

import com.example.appecommerce.entity.Category;
import com.example.appecommerce.payload.ApiResponse;
import com.example.appecommerce.payload.CategoryDto;
import com.example.appecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ApiResponse addCategory(CategoryDto categoryDto) {
        try {
            //Checking if category with this name exists
            if (categoryRepository.existsByName(categoryDto.getName())) {
                return new ApiResponse("Category with this name is already exists", false);
            }
            //create category and save it to DB
            categoryRepository.save(new Category(categoryDto.getName()));

            //return message with succes
            return new ApiResponse("Successfully saved", true);

            //If exception occurs tel user that problems occured
        } catch (Exception e) {
            //print stackTrace to analyze it later
            e.printStackTrace();
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse editCategory(UUID id, CategoryDto categoryDto) {
        try {
            //Get optionalCategory by category_id
            Optional<Category> optionalCategory = categoryRepository.findById(id);

            //if optional empty return message about it
            if (optionalCategory.isEmpty()) {
                return new ApiResponse("Category is not found", false);
            }

            //Get category from optional
            Category category = optionalCategory.get();

            //Change its name
            category.setName(categoryDto.getName());

            //save category to DB
            categoryRepository.save(category);
            return new ApiResponse("Successfully edited", true);

            //If exception occurs tell user that problems occurred
        } catch (Exception e) {
            //print stackTrace to analyze it later
            e.printStackTrace();
            return new ApiResponse("Problems occurred", false);
        }
    }

    @Override
    public ApiResponse deleteCategory(UUID id) {
        try {
            //Checking if category exists
            if (!categoryRepository.existsById(id)) {
                //return message
                return new ApiResponse("This category does not exists", false);
            }

            //If exists delete from DB
            categoryRepository.deleteById(id);

            //return message
            return new ApiResponse("Successfully deleted", true);

            //If exception occurs tel user that problems occurred
        } catch (Exception e) {
            //print stackTrace to analyze it later
            e.printStackTrace();
            return new ApiResponse("Problems occurred", false);
        }
    }
}

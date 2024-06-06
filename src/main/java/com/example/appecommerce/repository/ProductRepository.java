package com.example.appecommerce.repository;

import com.example.appecommerce.entity.Product;
import com.example.appecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * This method helps to know if a product with this name exists
     *
     * @param name name o the product
     * @return true if it exists, false otherwise
     */
    boolean existsByNameAndOwner(String name, User owner);

    /**
     * This method returns all Products of a category
     *
     * @param categoryName category name
     * @return List of products
     */
    List<Product> findAllByCategoryName(String categoryName);

    /**
     * This method return all products of a particular user
     * @param owner_id owner of the products
     * @return ApiResponse with message and success
     */
    List<Product> findAllByOwnerId(UUID owner_id);

    Page<Product> findAllByOwnerId(UUID owner_id, Pageable pageable);
}

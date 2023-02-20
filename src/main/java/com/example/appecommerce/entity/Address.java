package com.example.appecommerce.entity;

import com.example.appecommerce.entity.template.AbsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Address of the User
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address extends AbsEntity {

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String street;
}

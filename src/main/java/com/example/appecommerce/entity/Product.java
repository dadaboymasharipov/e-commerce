package com.example.appecommerce.entity;

import com.example.appecommerce.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Length(max = 500)
    @Column(nullable = false)
    private String description;

    private double price;//Evaluated in the $

    @OneToOne
    private Category category;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User owner;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Attachment attachment;
}

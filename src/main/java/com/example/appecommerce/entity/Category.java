package com.example.appecommerce.entity;

import com.example.appecommerce.entity.template.AbsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(value = AuditingEntityListener.class)
public class Category extends AbsEntity {
    @Column(nullable = false)
    private String name;
}

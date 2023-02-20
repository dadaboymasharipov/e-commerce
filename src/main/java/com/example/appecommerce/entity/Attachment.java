package com.example.appecommerce.entity;


import com.example.appecommerce.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * this entity id for uploaded pictures
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Attachment extends AbsEntity {

    private String name;

    private long size;

    private String contentType;

    private byte[] content;
}

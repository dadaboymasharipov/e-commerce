package com.example.appecommerce.entity;

import com.example.appecommerce.entity.template.AbsEntity;
import com.example.appecommerce.entity.template.Permissions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private List<Permissions> permissions;

    @Length(max = 500)
    private String description;

}

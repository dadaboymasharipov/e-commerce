package com.example.appecommerce.entity.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * This is a template class that all other entities derive from
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AbsEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;//Id of the object

    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;//Who created it

    @LastModifiedBy
    private UUID updatedBy;//Who updated it

    @CreationTimestamp
    private Timestamp createdAt;//When it is created

    @UpdateTimestamp
    private Timestamp updatedAt;//When it is updated
}

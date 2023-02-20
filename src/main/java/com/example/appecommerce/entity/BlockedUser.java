package com.example.appecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.sql.Timestamp;

import static jakarta.persistence.FetchType.LAZY;

/**
 * This entity is for blocked Emails
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//Type is Long because it takes less memory and UUID may be unnecesary

    @Email
    @Column(nullable = false)
    private String email;//Email of the user that is blocked from this system

    @Column(nullable = false)
    private String reason;//Why this user is blocked

    @CreationTimestamp
    private Timestamp addedDate;//When this email is blocked

    @ManyToOne(fetch = LAZY)
    @CreatedBy
    private User addedBy;//Who added this email
}

package com.example.appecommerce.repository;

import com.example.appecommerce.entity.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {

    boolean existsByEmail(String email);

    Optional<BlockedUser> findByEmail(String email);
}

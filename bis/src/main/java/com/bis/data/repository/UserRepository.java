package com.bis.data.repository;

import com.bis.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DATA LAYER — Spring Data JPA Repository
 * Handles all database operations for the User entity.
 * Hibernate generates the SQL under the hood.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Used by UserService.login() to find a user by username. */
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

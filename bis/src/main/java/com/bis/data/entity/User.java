package com.bis.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DATA LAYER — JPA Entity
 * Represents an authenticated user of the system.
 * Mapped to the "users" table via Hibernate ORM.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    /** Stores BCrypt hash — never a plaintext password. */
    @Column(nullable = false)
    private String passwordHash;

    /** One user owns many expenses (cascade deletes orphans). */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Expense> expenses = new ArrayList<>();
}

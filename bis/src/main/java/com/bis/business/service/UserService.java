package com.bis.business.service;

import com.bis.data.entity.User;
import com.bis.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * BUSINESS LAYER — UserService
 *
 * Implements F2: Authentication (login / logout).
 * Enforces the rule that passwords are always BCrypt-hashed
 * before being persisted — plaintext is never stored or compared directly.
 *
 * Depends on UserRepository (Data Layer) via constructor injection.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Authenticates a user by username + plaintext password.
     *
     * @param username the submitted username
     * @param password the submitted plaintext password
     * @return the matched User entity
     * @throws AuthenticationException if credentials are invalid
     */
    @Transactional(readOnly = true)
    public User login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new AuthenticationException("Username and password must not be empty.");
        }

        Optional<User> userOpt = userRepository.findByUsername(username.trim());

        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPasswordHash())) {
            log.warn("Failed login attempt for username: {}", username);
            throw new AuthenticationException("Invalid username or password.");
        }

        log.info("User '{}' logged in successfully.", username);
        return userOpt.get();
    }

    /**
     * Creates a new user with a hashed password.
     * Used by CsvImportService when importing users from CSV.
     *
     * @param username     the desired username
     * @param rawPassword  the plaintext password (will be hashed)
     * @return the persisted User entity
     */
    @Transactional
    public User createUser(String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            log.info("User '{}' already exists — skipping creation.", username);
            return userRepository.findByUsername(username).orElseThrow();
        }

        User user = User.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .build();

        User saved = userRepository.save(user);
        log.info("Created new user '{}'.", username);
        return saved;
    }
}

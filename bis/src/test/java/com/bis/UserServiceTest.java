package com.bis.business.service;

import com.bis.data.entity.User;
import com.bis.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService — Business Layer.
 * Tests authentication logic in isolation using Mockito.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        encoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, encoder);
    }

    // -------------------------------------------------------------------------
    // login()
    // -------------------------------------------------------------------------

    @Test
    void login_validCredentials_returnsUser() {
        String rawPassword = "secret123";
        User user = User.builder()
                .id(1L)
                .username("alice")
                .passwordHash(encoder.encode(rawPassword))
                .build();

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        User result = userService.login("alice", rawPassword);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("alice");
    }

    @Test
    void login_wrongPassword_throwsAuthenticationException() {
        User user = User.builder()
                .username("alice")
                .passwordHash(encoder.encode("correctPassword"))
                .build();

        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.login("alice", "wrongPassword"))
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("Invalid username or password");
    }

    @Test
    void login_unknownUser_throwsAuthenticationException() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login("ghost", "anyPassword"))
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void login_emptyUsername_throwsAuthenticationException() {
        assertThatThrownBy(() -> userService.login("", "password"))
                .isInstanceOf(AuthenticationException.class)
                .hasMessageContaining("must not be empty");
    }

    @Test
    void login_nullPassword_throwsAuthenticationException() {
        assertThatThrownBy(() -> userService.login("alice", null))
                .isInstanceOf(AuthenticationException.class);
    }

    // -------------------------------------------------------------------------
    // createUser()
    // -------------------------------------------------------------------------

    @Test
    void createUser_newUser_savesWithHashedPassword() {
        when(userRepository.existsByUsername("bob")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.createUser("bob", "plaintext");

        verify(userRepository).save(any(User.class));
        assertThat(result.getPasswordHash()).isNotEqualTo("plaintext");
        assertThat(encoder.matches("plaintext", result.getPasswordHash())).isTrue();
    }

    @Test
    void createUser_existingUser_skipsCreation() {
        User existing = User.builder().username("bob").passwordHash("hash").build();
        when(userRepository.existsByUsername("bob")).thenReturn(true);
        when(userRepository.findByUsername("bob")).thenReturn(Optional.of(existing));

        userService.createUser("bob", "anyPassword");

        verify(userRepository, never()).save(any());
    }
}

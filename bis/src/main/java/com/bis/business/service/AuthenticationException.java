package com.bis.business.service;

/**
 * BUSINESS LAYER — Domain Exception
 * Thrown when login credentials are invalid.
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}

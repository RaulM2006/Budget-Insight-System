package com.bis.business.service;

/**
 * BUSINESS LAYER — Domain Exception
 * Thrown when a CSV file does not conform to the expected format.
 */
public class InvalidFormatException extends RuntimeException {
    public InvalidFormatException(String message) {
        super(message);
    }
}

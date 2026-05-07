package com.bis.business.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BUSINESS LAYER — Value Object / DTO
 * Carries the result of a CSV import operation back to the Presentation Layer.
 */
@Getter
@RequiredArgsConstructor
public class ImportResult {

    private final int successCount;
    private final int failureCount;
    private final String summary;

    public boolean hasFailures() {
        return failureCount > 0;
    }

    @Override
    public String toString() {
        return summary;
    }
}

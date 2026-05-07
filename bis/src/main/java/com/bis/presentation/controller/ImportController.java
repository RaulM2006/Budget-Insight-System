package com.bis.presentation.controller;

import com.bis.business.service.CsvImportService;
import com.bis.business.service.ImportResult;
import com.bis.business.service.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * PRESENTATION LAYER — ImportController
 *
 * Mediates between DashboardWindow (View) and CsvImportService (Business Layer).
 * Matches the CSV sequence diagram: DashboardUI -> CsvImportService.importData()
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ImportController {

    private final CsvImportService csvImportService;

    /**
     * Delegates file import to the Business Layer.
     *
     * @param file the CSV file chosen by the user
     * @return ImportResult with success/failure counts
     * @throws InvalidFormatException if the file format is invalid
     */
    public ImportResult handleImport(File file) {
        log.info("Import requested for file: {}", file != null ? file.getName() : "null");
        return csvImportService.importData(file);
    }
}

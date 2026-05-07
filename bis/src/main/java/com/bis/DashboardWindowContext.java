package com.bis;

import com.bis.presentation.controller.DashboardController;
import com.bis.presentation.controller.ImportController;

/**
 * Simple static context holder so that DashboardWindow (which is created
 * by LoginController, not by Spring) can still access Spring-managed beans.
 */
public class DashboardWindowContext {

    private static ImportController importController;
    private static DashboardController dashboardController;

    public static void setImportController(ImportController controller) {
        DashboardWindowContext.importController = controller;
    }

    public static ImportController getImportController() {
        return importController;
    }

    public static void setDashboardController(DashboardController controller) {
        DashboardWindowContext.dashboardController = controller;
    }

    public static DashboardController getDashboardController() {
        return dashboardController;
    }
}
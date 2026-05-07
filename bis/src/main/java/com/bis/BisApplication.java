package com.bis;

import com.bis.presentation.controller.DashboardController;
import com.bis.presentation.controller.ImportController;
import com.bis.presentation.controller.LoginController;
import com.bis.presentation.view.LoginWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * APPLICATION ENTRY POINT
 *
 * Bootstraps both Spring Boot (for DI, ORM, database) and JavaFX (for the UI).
 */
@SpringBootApplication
public class BisApplication extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        springContext = SpringApplication.run(BisApplication.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LoginController loginController       = springContext.getBean(LoginController.class);
        ImportController importController     = springContext.getBean(ImportController.class);
        DashboardController dashboardController = springContext.getBean(DashboardController.class);

        DashboardWindowContext.setImportController(importController);
        DashboardWindowContext.setDashboardController(dashboardController);

        LoginWindow loginWindow = new LoginWindow(loginController);
        loginWindow.show();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }
}
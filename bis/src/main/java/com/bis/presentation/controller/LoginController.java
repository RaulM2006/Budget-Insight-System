package com.bis.presentation.controller;

import com.bis.business.service.AuthenticationException;
import com.bis.business.service.UserService;
import com.bis.data.entity.User;
import com.bis.presentation.view.DashboardWindow;
import com.bis.presentation.view.LoginWindow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * PRESENTATION LAYER — LoginController
 *
 * Mediates between LoginWindow (View) and UserService (Business Layer).
 * Follows the sequence diagram: LoginWindow -> UserService.handleLogin() -> UserWindow.show()
 *
 * The controller is Spring-managed so it can receive injected services,
 * but it does NOT contain any JavaFX UI code itself.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;
    private LoginWindow loginWindow;

    public void setView(LoginWindow loginWindow) {
        this.loginWindow = loginWindow;
    }

    /**
     * Handles the login action from the view.
     * On success: opens DashboardWindow and hides LoginWindow.
     * On failure: instructs the view to show an error message.
     */
    public void handleLogin(String username, String password) {
        try {
            User user = userService.login(username, password);
            // Open dashboard, hide login — matches sequence diagram
            DashboardWindow dashboard = new DashboardWindow(user, this);
            dashboard.show();
            if (loginWindow != null) {
                loginWindow.hide();
            }
        } catch (AuthenticationException e) {
            if (loginWindow != null) {
                loginWindow.showError(e.getMessage());
            }
        }
    }

    /**
     * Handles logout — closes dashboard and shows login again.
     */
    public void handleLogout() {
        log.info("User logged out.");
        if (loginWindow != null) {
            loginWindow.clearFields();
            loginWindow.show();
        }
    }
}

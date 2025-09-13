package testplans.authentication.login;

import framework.application.handlers.LoginHandler;
import framework.utils.ExcelUtil;
import resources.handler.ScreenshotUtil;

import org.junit.jupiter.api.Test;

import base.BaseTest;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import io.qameta.allure.*;

@Epic("Authentication")
@Feature("Login")
public class LoginTestSuite extends BaseTest {

    public LoginHandler loginHandler;
    public Class<? extends Enum<?>> loginSelector = allSectors.get("loginpage");

    String path = "src/test/java/resources/testdata/user-credentials.xlsx";
    Map<String, Map<String, String>> users;
    void setupUsers() {
        try {
            users = ExcelUtil.readExcel(path, "Sheet1");
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + path, e);
        }
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User logs in with valid credentials")
    public void testValidLogin() {
        // Implement test for valid login;
        this.setupUsers();
        LoginHandler loginHandler = new LoginHandler(page);
        ScreenshotUtil.captureScreenshot(page);
        for (Map.Entry<String, Map<String, String>> entry : users.entrySet()) {
            Map<String, String> userData = entry.getValue();
            String type = userData.get("type");
            if (!"valid".equalsIgnoreCase(type)) continue; // Skip non-valid users
            String username = userData.get("username");
            String password = userData.get("password");
            Allure.step("Testing login for user: " + username);

            Allure.step("Enter username: " + username, () -> {
                  Allure.step("Entered username: " + username);
            });

            Allure.step("Enter password: " + password, () -> {
                  Allure.step("Entered password: " + password);
            });
            

            // loginHandler.enterUsername(username);
            // loginHandler.enterPassword(password);
            // loginHandler.clickLoginButton();
            // Assertions.assertTrue(loginHandler.isUserLoggedIn());

            // // Logout after successful login to reset state for next user
            // loginHandler.clickLogoutButton();
        }
    }

    @Test
    public void testValidLoginWithSpecialCharacters() {
        loginHandler = new LoginHandler(page);
        ScreenshotUtil.captureScreenshot(page);
        Allure.step("Testing login with special characters in username and password");
        // Implement test for valid login with special characters
    }

    @Test
    public void testInvalidLoginWithWrongPassword() {
        loginHandler = new LoginHandler(page);
        ScreenshotUtil.captureScreenshot(page);
        ScreenshotUtil.captureScreenshot(page);
        Allure.step("Testing login with valid username and wrong password");
        // Implement test for invalid login
    }

    @Test
    public void testInvalidLoginWithEmptyUsername() {
        Allure.step("Testing login with empty username and valid password");
        // Implement test for invalid login
    }

    @Test
    public void testInvalidLoginWithEmptyPassword() {
        Allure.step("Testing login with valid username and empty password");
        // Implement test for invalid login
    }

    @Test
    public void testEmptyLoginFields() {
        Allure.step("Testing login with both username and password fields empty");
        // Implement test for empty login fields
    }

    @Test
    public void testInvalidLoginWithNonExistentUser() {
        Allure.step("Testing login with non-existent username and any password");
        // Implement test for invalid login
    }

    @Test
    public void testRememberMeFunctionality() {
        Allure.step("Testing 'Remember Me' functionality during login");
        // Implement test for "Remember Me" functionality
    }

    @Test
    public void testPasswordFunctionality() {
        Allure.step("Testing password visibility toggle and strength indicator");
        // Implement test for password functionality
    }

    @Test
    public void testMultipleFailedLoginAttempts() {
        Allure.step("Testing account lockout after multiple failed login attempts");
        // Implement test for multiple failed login attempts
    }

    @Test
    public void testForgotPassword() {
        Allure.step("Testing 'Forgot Password' functionality");
        // Implement test for "Forgot Password" functionality
    }

    @Test
    public void testChangeUnexpiredPassword() {
        Allure.step("Testing changing unexpired password functionality");
        // Implement test for changing unexpired password
    }

    @Test
    public void testChangeExpiredPassword() {
        Allure.step("Testing changing expired password functionality");
        // Implement test for changing expired password
    }


}

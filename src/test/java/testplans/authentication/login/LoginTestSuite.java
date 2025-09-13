package testplans.authentication.login;

import framework.application.handlers.LoginHandler;
import framework.utils.AllureUtil;
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
    @Description("User logs in with valid credentials\n" +
    "Procedure:\n" +
    "1. Read user credentials from Excel.\n" +
    "2. For each user with type 'valid':\n" +
    "   a. Navigate to the login page.\n" +
    "   b. Enter the username.\n" +
    "   c. Enter the password.\n" +
    "   d. Click the login button.\n" +
    "   e. Verify the user is logged in.\n" +
    "   f. Log out to reset state for next user.")
    public void testValidLogin() {

        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");

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

            Allure.step("Testing login for user: " + username, () -> {
                Allure.step("Enter username: " + username, () -> {
                    Allure.step("Entered username: " + username);
                });

                Allure.step("Enter password: " + password, () -> {
                    Allure.step("Entered password: " + password);
                });
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
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User logs in with valid credentials containing special characters\n" +
    "Procedure:\n" +
    "1. Navigate to the login page.\n" +
    "2. Enter a username with special characters (e.g., user!@#).\n" +
    "3. Enter a password with special characters (e.g., Pass@123!).\n" +
    "4. Click the login button.\n" +
    "5. Verify that the user is successfully logged in and redirected to the dashboard.\n" +
    "6. Log out to reset the state for subsequent tests.")
    public void testValidLoginWithSpecialCharacters() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        loginHandler = new LoginHandler(page);
        ScreenshotUtil.captureScreenshot(page);
        Allure.step("Testing login with special characters in username and password");
        // Implement test for valid login with special characters
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("User attempts to log in with valid username and wrong password\n" +
    "Procedure:\n" +
    "1. Navigate to the login page.\n" +
    "2. Enter a valid username.\n" +
    "3. Enter an incorrect password.\n" +
    "4. Click the login button.\n" +
    "5. Verify that login fails and an error message is shown.")
    public void testInvalidLoginWithWrongPassword() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        loginHandler = new LoginHandler(page);
        ScreenshotUtil.captureScreenshot(page);
        Allure.step("Testing login with valid username and wrong password");
        // Implement test for invalid login
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("User attempts to log in with empty username and valid password")
    public void testInvalidLoginWithEmptyUsername() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing login with empty username and valid password");
        // Implement test for invalid login
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("User attempts to log in with valid username and empty password")
    public void testInvalidLoginWithEmptyPassword() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing login with valid username and empty password");
        // Implement test for invalid login
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("User attempts to log in with both username and password fields empty")
    public void testEmptyLoginFields() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing login with both username and password fields empty");
        // Implement test for empty login fields
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("User attempts to log in with non-existent username and any password")
    public void testInvalidLoginWithNonExistentUser() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing login with non-existent username and any password");
        // Implement test for invalid login
    }

    @Test
    @Story("Login Features")
    @Severity(SeverityLevel.MINOR)
    @Description("User tests 'Remember Me' functionality during login")
    public void testRememberMeFunctionality() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing 'Remember Me' functionality during login");
        // Implement test for "Remember Me" functionality
    }

    @Test
    public void testPasswordFunctionality() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing password visibility toggle and strength indicator");
        // Implement test for password functionality
    }

    @Test
    public void testMultipleFailedLoginAttempts() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing account lockout after multiple failed login attempts");
        // Implement test for multiple failed login attempts
    }

    @Test
    public void testForgotPassword() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing 'Forgot Password' functionality");
        // Implement test for "Forgot Password" functionality
    }

    @Test
    public void testChangeUnexpiredPassword() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing changing unexpired password functionality");
        // Implement test for changing unexpired password
    }

    @Test
    public void testChangeExpiredPassword() {
        AllureUtil.testCaseInfo("Alice", "Bob", "2024-10-01");
        Allure.step("Testing changing expired password functionality");
        // Implement test for changing expired password
    }


}

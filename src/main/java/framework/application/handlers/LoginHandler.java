package framework.application.handlers;

import framework.application.pages.LoginPage;
import com.microsoft.playwright.Page;

public class LoginHandler {

    private final Page page;

    public LoginHandler(Page page) {
        this.page = page;
    }

    public void navigateToLoginPage(String url) {
        page.navigate(url);
    }

    public void enterUsername(String username) {
        page.fill(LoginPage.IPB_USERNAME.getSelector(), username);
    }

    public void enterPassword(String password) {
        page.fill(LoginPage.IPB_PASSWORD.getSelector(), password);
    }

    public void clickLoginButton() {
        page.click(LoginPage.BTN_LOGIN.getSelector());
    }

    public String getErrorMessage() {
        return page.textContent(LoginPage.TD_ERROR_MESSAGE.getSelector());
    }

    public void clickLogoutButton() {
        page.click(LoginPage.BTN_LOGOUT.getSelector());
    }

    public void clickProfileMenu() {
        page.click(LoginPage.DR_PROFILE_MENU.getSelector());
    }

    public void clickChangePasswordLink() {
        page.click(LoginPage.LNK_CHANGE_PASSWORD.getSelector());
    }

    public void enterNewPassword(String newPassword) {
        page.fill(LoginPage.IPB_NEW_PASSWORD.getSelector(), newPassword);
    }

    public void enterConfirmPassword(String confirmPassword) {
        page.fill(LoginPage.IPB_CONFIRM_PASSWORD.getSelector(), confirmPassword);
    }

    public void clickChangePasswordButton() {
        page.click(LoginPage.BTN_CHANGE_PASSWORD.getSelector());
    }

    public String getSuccessMessage() {
        return page.textContent(LoginPage.TD_SUCCESS_MESSAGE.getSelector());
    }

    public void clickForgotPasswordLink() {
        page.click(LoginPage.LNK_FORGOT_PASSWORD.getSelector());
    }

    public void enterResetEmail(String email) {
        page.fill(LoginPage.IPB_RESET_EMAIL.getSelector(), email);
    }

    public void clickResetButton() {
        page.click(LoginPage.BTN_RESET.getSelector());
    }

    public void toggleRememberMe() {
        page.check(LoginPage.REMEMBER_ME_CHECKBOX.getSelector());
    }

    public boolean isRememberMeChecked() {
        return page.isChecked(LoginPage.REMEMBER_ME_CHECKBOX.getSelector());
    }

    public boolean isUserLoggedIn() {
        return page.isVisible(LoginPage.BTN_LOGOUT.getSelector());
    }

    public boolean isUserLoggedOut() {
        return page.isVisible(LoginPage.BTN_LOGIN.getSelector());
    }
}

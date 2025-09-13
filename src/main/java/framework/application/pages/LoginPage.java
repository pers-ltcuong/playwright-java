package framework.application.pages;

public enum LoginPage {

    // Input boxes
    IPB_USERNAME("#username"),
    IPB_PASSWORD("#password"),
    IPB_NEW_PASSWORD("#newPassword"),
    IPB_CONFIRM_PASSWORD("#confirmPassword"),
    IPB_RESET_EMAIL("#resetEmail"),

    // Buttons
    BTN_LOGIN("#loginButton"),
    BTN_LOGOUT("#logoutButton"),
    BTN_CHANGE_PASSWORD("#changePasswordButton"),
    BTN_RESET("#resetButton"),

    // Checkboxes
    REMEMBER_ME_CHECKBOX("#rememberMe"),

    // Links
    LNK_FORGOT_PASSWORD("#forgotPasswordLink"),
    LNK_CHANGE_PASSWORD("#changePasswordLink"),

    // Dropdowns
    DR_PROFILE_MENU("#profileMenu"),

    // Text area (example)
    TXTA_COMMENTS("#comments"),

    // Text display / labels
    TD_ERROR_MESSAGE("#errorMessage"),
    TD_SUCCESS_MESSAGE("#successMessage");

    private final String selector;

    LoginPage(String selector) {
        this.selector = selector;
    }

    public String getSelector() {
        return selector;
    }
}

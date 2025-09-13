package pretest;
import com.microsoft.playwright.*;

import framework.application.handlers.LoginHandler;
import framework.utils.ConfigReader;
import framework.utils.SelectorUtil;
import resources.handler.ScreenshotUtil;

import java.util.Map;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected Page page;
    protected static ConfigReader config;

    // Access allSectors map from SelectorUtil
    protected static final Map<String, Class<? extends Enum<?>>> allSectors = SelectorUtil.allSectors;

    @BeforeAll
    void globalSetup(TestInfo testInfo) {
        // Determine environment (default to 'test' if not specified)
        String env = System.getProperty("env", "test"); 
        config = new ConfigReader(env);

        boolean headless = Boolean.parseBoolean(config.getValue("headless"));
        String browserType = config.getValue("browser") != null ? config.getValue("browser") : "chromium";

        playwright = Playwright.create();
        switch (browserType.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                break;
            default:
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        }
        
        context = browser.newContext();
        page = context.newPage();
        page.navigate(config.getValue("baseUrl"));
        // Check if the test class name contains "Login"
        String className = testInfo.getTestClass()
                                   .map(Class::getSimpleName)
                                   .orElse("");

        if (!className.contains("Login")) {
            // Perform login if NOT running a Login* test
            LoginHandler loginHandler = new LoginHandler(page);
            loginHandler.enterUsername(config.getValue("username"));
            loginHandler.enterPassword(config.getValue("password"));
            loginHandler.clickLoginButton();
        }
    }

    @BeforeEach
    void setupEach(TestInfo info) {
        // This can be used to reset state before each test if needed
        ScreenshotUtil.startTest(info); // reset counter for this test
    }

    @AfterAll
    static void globalTeardown() {
        context.close();
        browser.close();
        playwright.close();
    }
}

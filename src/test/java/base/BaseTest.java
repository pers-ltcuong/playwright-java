package base;
import com.microsoft.playwright.*;

import framework.application.handlers.LoginHandler;
import framework.utils.AllureUtil;
import framework.utils.ConfigReader;
import framework.utils.ScreenshotUtil;
import framework.utils.SelectorUtil;

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

    // Global setup before all tests
    @BeforeAll
    void globalSetup(TestInfo testInfo) {
        // Determine environment (default to 'test' if not specified)
        String env = System.getProperty("env", "test"); 
        config = new ConfigReader(env);

        // Declare browser options
        boolean headless = Boolean.parseBoolean(config.getValue("headless"));
        String browserType = config.getValue("browser") != null ? config.getValue("browser") : "chromium";

        // Write Allure environment properties
        AllureUtil.writeEnvironment(config);
        AllureUtil.writeEnvironment("Browser", browserType);
        AllureUtil.writeEnvironment("Headless", String.valueOf(headless));

        // Initialize Playwright and browser
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
        
        // Create context and page
        context = browser.newContext();
        page = context.newPage();

        // Navigate to URL under test, using a browser which navigated to the base URL for all tests
        page.navigate(config.getValue("baseUrl"));

        // Check if the test class name contains "Login". If not, perform login for other tests. Otherwise, skip login for Login tests.
        String className = testInfo.getTestClass()
                                   .map(Class::getSimpleName)
                                   .orElse("");

        if (!className.contains("Login")) {
            LoginHandler loginHandler = new LoginHandler(page);
            loginHandler.enterUsername(config.getValue("username"));
            loginHandler.enterPassword(config.getValue("password"));
            loginHandler.clickLoginButton();
        }
    }

    // Initialize ScreenshotUtil for each test
    @BeforeEach
    void setupEach(TestInfo info) {
        ScreenshotUtil.startTest(info); // reset counter for this test
    }

    @AfterEach
    void teardownEach() {
       // Implement any per-test cleanup if needed
    }

    // Close resources after all tests
    @AfterAll
    static void globalTeardown() {
        context.close();
        browser.close();
        playwright.close();
    }
}

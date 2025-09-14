package framework.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotUtil {

    private static ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    /** Call at the start of each test */
    public static void startTest(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        if (displayName == null || displayName.isBlank()) {
            displayName = "UnknownTest";
        }

        testName.set(displayName.replaceAll("\\s+", "_").toUpperCase());
        counter.set(0);

    }

    /** Capture screenshot with auto-name and attach to Allure */
    public static void captureScreenshot(Page page) {
        int current = counter.get() + 1;
        counter.set(current);

        String name = testName.get() + "_SS" + current + ".png";

        // Take screenshot
        byte[] bytes = takeScreenshot(page);

        // Save to disk (optional)
        Path screenshotPath = Paths.get("target/screenshots", name);
        try {
            Files.createDirectories(screenshotPath.getParent());
            Files.write(screenshotPath, bytes);
            System.out.println("Screenshot saved: " + screenshotPath.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Attach to Allure using InputStream
        try (InputStream is = Files.newInputStream(screenshotPath)) {
            Allure.step("Screenshot captured: " + name, () -> {
                Allure.attachment(name, is);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Actually take screenshot from Playwright page */
    private static byte[] takeScreenshot(Page page) {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }
}

package resources.handler;

import com.microsoft.playwright.Page;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScreenshotUtil {

    private static ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    /** Call at the start of each test */
    public static void startTest(TestInfo testInfo) {
        testName.set(testInfo.getTestMethod().map(m -> m.getName()).orElse("UnknownTest"));
        counter.set(0);
    }

    /** Capture screenshot with auto-name and attach to Allure */
    public static void captureScreenshot(Page page) {
        int current = counter.get() + 1;
        counter.set(current);

        String name = testName.get() + "_" + current;
        byte[] bytes = takeScreenshot(page);

        // Optional: save to disk
        try {
            Path dir = Path.of("target/screenshots");
            Files.createDirectories(dir);
            Path path = dir.resolve(name + ".png");
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Attach to Allure
        attachToAllure(bytes, name);
    }

    /** Internal method: returns byte[] for Allure */
    @Attachment(value = "{name}", type = "image/png")
    private static byte[] attachToAllure(byte[] bytes, String name) {
        return bytes;
    }

    /** Actually take screenshot from Playwright page */
    private static byte[] takeScreenshot(Page page) {
        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }
}

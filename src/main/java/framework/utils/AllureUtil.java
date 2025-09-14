package framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import io.qameta.allure.Allure;

public class AllureUtil {

    private static final String ENV_FILE = "target/allure-results/environment.properties";

    // Write environment from ConfigReader (batch)
    public static void writeEnvironment(ConfigReader config) {
        writeEnvironment("Base URL", config.getValue("baseUrl"));
        writeEnvironment("OS", System.getProperty("os.name"));
        writeEnvironment("API endpoint", config.getValue("apiEndpoint") != null ? config.getValue("apiEndpoint") : "N/A");
    }

    // Write a single key-value environment property (append/update)
    public static void writeEnvironment(String env, String value) {
        try {
            File file = new File(ENV_FILE);

            // Ensure parent directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            Properties props = new Properties();

            // Load existing properties if file exists
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    props.load(fis);
                }
            }

            // Add or update the property
            props.setProperty(env, value);

            // Save back to the file
            try (FileOutputStream fos = new FileOutputStream(file)) {
                props.store(fos, "Allure Environment Properties");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeParameter(String name, String value) {
        Allure.label(name, value);
    }

    public static void testCaseInfo(String creator, String reviewer, String date) {
        writeParameter("Testcase creator", creator);
        writeParameter("Peer reviewer", reviewer);
        writeParameter("Date creation", date);
    }
}

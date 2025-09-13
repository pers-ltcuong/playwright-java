package framework.utils;

import framework.application.pages.LoginPage;
// import other page enums as needed

import java.util.HashMap;
import java.util.Map;

public class SelectorUtil {

    // Map page name to enum class
    public static final Map<String, Class<? extends Enum<?>>> allSectors = new HashMap<>();

    static {
        allSectors.put("loginpage", LoginPage.class);
        // add other pages here
    }

}
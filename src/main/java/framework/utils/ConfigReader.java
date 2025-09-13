package framework.utils;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class ConfigReader {

    private Document doc;

    public ConfigReader(String env) {
        try {
            String filePath = "src/main/java/resources/env/" + env + ".xml";
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config for env: " + env + ", " + e.getMessage());
        }
    }

    public String getValue(String tagName) {
        NodeList nodeList = doc.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) return null;
        return nodeList.item(0).getTextContent();
    }
}

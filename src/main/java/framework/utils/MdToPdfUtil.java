package framework.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class MdToPdfUtil {

    /**
     * Merge multiple Markdown files into one PDF.
     * Each file starts on a new page.
     *
     * @param testClass   reference to the calling test suite class
     * @param mdFilenames list of .md files (relative to the suite's directory)
     * @param outputPdf   output PDF file name (null or empty = "MergedDocument.pdf")
     */
    public void mergeMarkdownToPdf(Class<?> testClass, List<String> mdFilenames, String outputPdf) throws Exception {

        // Convert package -> directory path
        String pkgPath = testClass.getPackageName().replace(".", "/");

        // Suite directory inside src/test/java
        Path currentDir = Path.of(System.getProperty("user.dir"), "src/test/java", pkgPath);

        // If mdFilenames is null → default list
        if (mdFilenames == null) {
            mdFilenames = List.of("Chapter1.md", "RevisionHistory.md", "ReviewHistory.md");
        }

        // If outputPdf is null or empty → default name
        if (outputPdf == null || outputPdf.trim().isEmpty()) {
            outputPdf = "Testplan.pdf";
        }

        Parser parser = Parser.builder()
        .extensions(Arrays.asList(TablesExtension.create()))
        .build();

        HtmlRenderer renderer = HtmlRenderer.builder()
        .extensions(Arrays.asList(TablesExtension.create()))
        .build();

        StringBuilder combinedHtml = new StringBuilder("<html><head>")
            .append("<style>")
            .append("body { font-family: Arial, sans-serif; }")
            .append("table { border-collapse: collapse; width: 100%; margin: 12px 0; }")
            .append("th, td { border: 1px solid #000; padding: 6px; text-align: left; }")
            .append("th { background-color: #f2f2f2; }")
            .append("</style></head><body>");

        boolean first = true;
        for (String mdFilename : mdFilenames) {
            Path md = currentDir.resolve(mdFilename);

            String markdown = Files.readString(md);
            String html = renderer.render(parser.parse(markdown));

            if (!first) {
                // Insert page break BEFORE starting next file
                combinedHtml.append("<div style='page-break-before: always;'></div>");
            }

            combinedHtml.append(html);
            first = false;
        }

        combinedHtml.append("</body></html>");

        Path pdf = currentDir.resolve(outputPdf);

        try (FileOutputStream os = new FileOutputStream(pdf.toFile())) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(combinedHtml.toString(), null);
            builder.toStream(os);
            builder.run();
        }

        System.out.println("Merged PDF created at: " + pdf);
    }
}

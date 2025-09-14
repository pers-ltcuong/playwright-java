package framework.utils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
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
     * @param outputPdf   output PDF file name (null or empty = "Testplan.pdf")
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

        // Enable GitHub Flavored Markdown extensions
        List extensions = Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                TaskListExtension.create(),
                AutolinkExtension.create()
        );

        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        StringBuilder combinedHtml = new StringBuilder("<html><head>")
                .append("<style>")
                .append("body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 1.6; }")
                .append("h1, h2, h3, h4 { font-weight: 600; margin-top: 24px; }")
                .append("pre, code { background-color: #f6f8fa; padding: 4px; border-radius: 4px; }")
                .append("table { border-collapse: collapse; width: 100%; margin: 16px 0; }")
                .append("th, td { border: 1px solid #dfe2e5; padding: 6px 13px; }")
                .append("th { background-color: #f2f2f2; font-weight: 600; }")
                .append("ul.task-list { list-style: none; }")
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

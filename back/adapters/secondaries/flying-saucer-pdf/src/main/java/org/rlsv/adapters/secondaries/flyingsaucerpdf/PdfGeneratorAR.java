package org.rlsv.adapters.secondaries.flyingsaucerpdf;

import com.lowagie.text.DocumentException;
import domain.models.DevisDN;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ports.pdfs.ProviderPdfPT;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class PdfGeneratorAR implements ProviderPdfPT {

    public static final String REPORT_DEVIS = "devis";
    public static final String RESOURCE_FONT_SYNE = "/fonts/syne/Syne-VariableFont_wght.ttf";

    private TemplateEngine thymeleafTemplateEngine;

    public PdfGeneratorAR() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        thymeleafTemplateEngine = new TemplateEngine();
        thymeleafTemplateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public ByteArrayOutputStream genererDevisPDF(DevisDN devis) {

        Context context = new Context();
        context.setVariable("devis", devis);

        return processTemplateToPdf(REPORT_DEVIS, RESOURCE_FONT_SYNE, context);
    }

    private ByteArrayOutputStream processTemplateToPdf(String reportName, String fontResource, Context context) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            String html = thymeleafTemplateEngine.process(reportName, context);

            ITextRenderer renderer = new ITextRenderer();

            URL url = PdfGeneratorAR.class.getResource(fontResource);
            renderer.getFontResolver().addFont(url.getPath(),true);
            renderer.setDocumentFromString(html);
            renderer.layout();

            renderer.createPDF(baos);

            return baos;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

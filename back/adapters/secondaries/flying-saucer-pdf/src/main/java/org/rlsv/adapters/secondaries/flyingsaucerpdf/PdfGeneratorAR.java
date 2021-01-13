package org.rlsv.adapters.secondaries.flyingsaucerpdf;

import com.lowagie.text.DocumentException;
import domains.models.DevisDN;
import exceptions.PdfException;
import org.apache.commons.collections4.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import ports.pdfs.ProviderPdfPT;
import utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import static localizations.MessageKeys.PDF_GENERATION_ERROR;

public class PdfGeneratorAR implements ProviderPdfPT {

    public static final String REPORT_DEVIS = "devis";
    public static final String RESOURCE_FONT_SYNE = "/fonts/syne/Syne-VariableFont_wght.ttf";
    public static final String RESOURCE_FONT_NUNITO = "/fonts/nunito/Nunito-Regular.ttf";

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
    public ByteArrayOutputStream genererDevisPDF(DevisDN devis) throws PdfException {

        Context context = new Context();
        context.setVariable("devis", devis);

        if (CollectionUtils.isNotEmpty(devis.getDevisLigneList())) {
            BigDecimal sommeHT = devis.getDevisLigneList().stream().map(item -> item.getMontantHT()).reduce(BigDecimal.ZERO, Utils::add);
            context.setVariable("sommeHT", sommeHT);

            BigDecimal sommeTVA = sommeHT.multiply(devis.getTva().divide(new BigDecimal(100)));
            context.setVariable("sommeTVA", sommeTVA);

            context.setVariable("sommeTTC", sommeHT.add(sommeTVA));
        }


        return processTemplateToPdf(REPORT_DEVIS, RESOURCE_FONT_NUNITO, context);
    }

    private ByteArrayOutputStream processTemplateToPdf(String reportName, String fontResource, Context context) throws PdfException {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            String html = thymeleafTemplateEngine.process(reportName, context);

            ITextRenderer renderer = new ITextRenderer();

            URL url = PdfGeneratorAR.class.getResource(fontResource);
            renderer.getFontResolver().addFont(url.getPath(), true);
            renderer.setDocumentFromString(html);
            renderer.layout();

            renderer.createPDF(baos);

            return baos;
        } catch (DocumentException e) {
            throw new PdfException(e.getMessage(), e, PDF_GENERATION_ERROR);
        } catch (IOException e) {
            throw new PdfException(e.getMessage(), e, PDF_GENERATION_ERROR);
        }


    }

}

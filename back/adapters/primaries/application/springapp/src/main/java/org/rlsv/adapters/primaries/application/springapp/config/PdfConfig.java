package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.flyingsaucerpdf.PdfGeneratorAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.pdfs.ProviderPdfPT;


@Configuration
public class PdfConfig {



    @Bean
    public ProviderPdfPT providerPdfPT() {
        return new PdfGeneratorAR();
    }

}

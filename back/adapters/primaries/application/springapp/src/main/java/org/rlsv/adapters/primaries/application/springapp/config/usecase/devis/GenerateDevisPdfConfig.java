package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.transactions.TransactionManagerPT;
import usecases.devis.GenerateDevisPdfUE;

@Configuration
public class GenerateDevisPdfConfig {

    @Bean
    public GenerateDevisPdfUE generateDevisPdfUE(LocalizeServicePT localizeService,
                                                 TransactionManagerPT transactionManager,
                                                 ProviderPdfPT providerPdfPT
    ) {
        return new GenerateDevisPdfUE(localizeService, transactionManager, providerPdfPT);
    }


}

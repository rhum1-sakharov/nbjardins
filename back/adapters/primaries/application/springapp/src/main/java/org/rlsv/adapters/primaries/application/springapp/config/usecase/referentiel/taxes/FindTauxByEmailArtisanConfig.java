package org.rlsv.adapters.primaries.application.springapp.config.usecase.referentiel.taxes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.referentiel.taxes.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.referentiel.taxes.FindTauxByEmailArtisanUE;

@Configuration
public class FindTauxByEmailArtisanConfig {

    @Bean
    public FindTauxByEmailArtisanUE findTauxByEmailArtisanUE(LocalizeServicePT localizeService,
                                                             TransactionManagerPT transactionManager,
                                                             TaxeRepoPT taxeRepo
    ) {
        return new FindTauxByEmailArtisanUE(localizeService, transactionManager, taxeRepo);
    }


}

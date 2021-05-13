package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes;

import helpers.search.filters.SearchFilterHelper;
import keys.client.ClientKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ports.localization.LocalizeServicePT;
import ports.repositories.personnes.artisans.banques.ArtisanBanqueRepoPT;
import ports.repositories.personnes.artisans.options.ArtisanOptionRepoPT;
import ports.repositories.personnes.clients.ClientRepoPT;
import ports.transactions.TransactionManagerPT;
import usecases.personnes.artisans.banques.RemoveArtisanBanqueByEmailUE;
import usecases.personnes.artisans.banques.SaveArtisanBanqueUE;
import usecases.personnes.artisans.options.SaveOptionUE;
import usecases.personnes.clients.FindByEmailUE;
import usecases.personnes.clients.FindClientsOfArtisanUE;
import usecases.personnes.clients.SearchClientUE;


@Configuration
public class PersonneConfig {

    @Bean
    public FindByEmailUE clientFindByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ClientRepoPT clientRepo) {
        return new FindByEmailUE(ls, tm, clientRepo);
    }

    @Bean
    public FindClientsOfArtisanUE findClientsOfArtisanUE(LocalizeServicePT ls, TransactionManagerPT tm, ClientRepoPT clientRepo) {
        return new FindClientsOfArtisanUE(ls, tm, clientRepo);
    }

    @Bean
    public usecases.personnes.artisans.options.FindByEmailUE optionsFindAllByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanOptionRepoPT artisanOptionRepo) {
        return new usecases.personnes.artisans.options.FindByEmailUE(ls, tm, artisanOptionRepo);
    }

    @Bean
    public SaveOptionUE artisanSaveOptionUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanOptionRepoPT artisanOptionRepo) {
        return new SaveOptionUE(ls, tm, artisanOptionRepo);
    }

    @Bean
    public SaveArtisanBanqueUE saveArtisanBanqueUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanBanqueRepoPT artisanBanqueRepo) {
        return new SaveArtisanBanqueUE(ls, tm, artisanBanqueRepo);
    }

    @Bean
    public RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanBanqueRepoPT artisanBanqueRepo) {
        return new RemoveArtisanBanqueByEmailUE(ls, tm, artisanBanqueRepo);
    }

    @Bean
    public usecases.personnes.artisans.banques.FindByEmailUE artisanBanqueFindByEmailUE(LocalizeServicePT ls, TransactionManagerPT tm, ArtisanBanqueRepoPT artisanBanqueRepo) {
        return new usecases.personnes.artisans.banques.FindByEmailUE(ls, tm, artisanBanqueRepo);
    }

    @Bean
    public SearchClientUE searchClientUE(LocalizeServicePT ls, TransactionManagerPT tm, ClientRepoPT clientRepo, SearchFilterHelper<ClientKey> sfh){
        return new SearchClientUE(ls,tm,clientRepo,sfh);
    }

    @Bean
    public PersonneUsecases personneUsecases(FindByEmailUE clientFindByEmailUE,
                                             FindClientsOfArtisanUE findClientsOfArtisanUE,
                                             usecases.personnes.artisans.options.FindByEmailUE optionsFindAllByEmailUE,
                                             SaveOptionUE artisanSaveOptionUE,
                                             SaveArtisanBanqueUE saveArtisanBanqueUE,
                                             RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE,
                                             usecases.personnes.artisans.banques.FindByEmailUE artisanBanqueFindByEmailUE,
                                             SearchClientUE searchClientUE
    ) {

        return PersonneUsecases.builder()
                .clientFindByEmailUE(clientFindByEmailUE)
                .findClientsOfArtisanUE(findClientsOfArtisanUE)
                .optionsFindAllByEmailUE(optionsFindAllByEmailUE)
                .artisanSaveOptionUE(artisanSaveOptionUE)
                .saveArtisanBanqueUE(saveArtisanBanqueUE)
                .removeArtisanBanqueByEmailUE(removeArtisanBanqueByEmailUE)
                .artisanBanqueFindByEmailUE(artisanBanqueFindByEmailUE)
                .searchClientUE(searchClientUE)
                .build();

    }


}

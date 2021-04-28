package org.rlsv.adapters.primaries.application.springapp.config.usecase.personnes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import usecases.personnes.artisans.banques.RemoveArtisanBanqueByEmailUE;
import usecases.personnes.artisans.banques.SaveArtisanBanqueUE;
import usecases.personnes.artisans.options.SaveOptionUE;
import usecases.personnes.clients.FindByEmailUE;
import usecases.personnes.clients.FindClientsOfArtisanUE;

@Getter
@Setter
@Builder
public class PersonneUsecases {

    FindByEmailUE clientFindByEmailUE;

    FindClientsOfArtisanUE findClientsOfArtisanUE;

    usecases.personnes.artisans.options.FindByEmailUE optionsFindAllByEmailUE;

    SaveOptionUE artisanSaveOptionUE;

    SaveArtisanBanqueUE saveArtisanBanqueUE;

    RemoveArtisanBanqueByEmailUE removeArtisanBanqueByEmailUE;

    usecases.personnes.artisans.banques.FindByEmailUE artisanBanqueFindByEmailUE;

}

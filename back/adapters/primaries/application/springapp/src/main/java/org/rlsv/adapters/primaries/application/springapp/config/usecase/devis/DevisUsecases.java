package org.rlsv.adapters.primaries.application.springapp.config.usecase.devis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import usecases.devis.*;
import usecases.devis.options.FindAllByDevisUE;
import usecases.devis.options.SaveOptionUE;

@Getter
@Setter
@Builder
public class DevisUsecases {

    DemandeDeDevisUE demandeDeDevisUE;

    ChangeStatusDevisUE changeStatusDevisUE;

    CountByEmailArtisanAndStatutUE countByEmailArtisanAndStatutUE;

    CreateDevisATraiterUE createDevisATraiterUE;

    RemoveDevisUE deleteDevisUE;

    FindByEmailArtisanAndStatutUE findByEmailArtisanAndStatutUE;

    FindByEmailArtisanUE findByEmailArtisanUE;

    FindByIdDevisUE findByIdDevisUE;

    GenerateDevisPdfUE generateDevisPdfUE;

    SaveDevisUE saveDevisUE;

    SaveOptionUE devisSaveOptionUE;

    FindAllByDevisUE devisOptionFindAllByDevisUE;

}

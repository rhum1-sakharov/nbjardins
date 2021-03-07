import {Component, Input, OnInit} from '@angular/core';
import {MArtisan, MArtisanBanque, MArtisanOption, MODELE_OPTION} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-preview-devis',
  templateUrl: './preview-devis.component.html',
  styleUrls: ['./preview-devis.component.scss']
})
export class PreviewDevisComponent implements OnInit {

  @Input() artisan !: MArtisan;
  @Input() artisanBanque !: MArtisanBanque;
  @Input() artisanOptionList !: MArtisanOption[];

  coordonneesBanquaires = MODELE_OPTION.COORDONNEES_BANQUAIRES;
  colonneQuantite = MODELE_OPTION.COLONNE_QUANTITE;
  tvaSaisissableParLigne = MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE;

  previewDate = new Date();


  constructor() {
  }

  ngOnInit(): void {
  }

  isActif(modeleOption: MODELE_OPTION) {

    for (const mArtisanOption of this.artisanOptionList) {

      if (mArtisanOption.modeleOption === modeleOption) {
        return mArtisanOption.actif;
      }

    }

    return false;
  }


}

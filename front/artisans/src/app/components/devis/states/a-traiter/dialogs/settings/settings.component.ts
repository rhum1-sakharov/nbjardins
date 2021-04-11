import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevisOption, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../../../../services/announces/devis-announces.service';
import {DevisHttpService} from '../../../../../../services/http/devis-http.service';
import {switchMap} from 'rxjs/operators';
import {MODELE_OPTION} from '../../../../../../../../../core-lib/dist/core-lib';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialog !: Subscription;
  devisOptionList !: MDevisOption[];

  constructor(private devisAnnounceSvc: DevisAnnouncesService,
              private devisHttpSvc: DevisHttpService,) {
    super();
  }

  ngOnInit(): void {

    this.openDialogSubscription();
  }

  openDialogSubscription() {
    this.subOpenDialog = this.devisAnnounceSvc.openDialogDevisOptions$.pipe(
      switchMap(response => this.devisHttpSvc.findOptionsDevis(response.idDevis))
    )
      .subscribe((response:any) => {
        this.displayDialog = true;
        this.devisOptionList = response.data.devisOptionFindByIdDevis;
      });
  }

  saveOptionsDevis() {
    this.close();
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialog);
  }

  getDevisOption(mo: MODELE_OPTION) {

    if (this.devisOptionList) {
      for (const ao of this.devisOptionList) {
        if (ao.modeleOption === mo) {
          return ao.actif;
        }
      }
    }

    return null;
  }


  setDevisOption(mo: MODELE_OPTION, $event: any) {
    if (this.devisOptionList) {
      for (const ao of this.devisOptionList) {
        if (ao.modeleOption === mo) {
          ao.actif = $event;
        }
      }
    }
  }

  getModeleOptionLabel(modeleOption: MODELE_OPTION) {

    switch (modeleOption) {
      case MODELE_OPTION.COLONNE_QUANTITE:
        return 'Afficher les colonnes quantité et prix unitaire HT';
      case MODELE_OPTION.COORDONNEES_BANQUAIRES:
        return 'Afficher les coordonnées banquaires';
      case MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE:
        return 'TVA saisissable par ligne';
      case MODELE_OPTION.PROVISION:
        return 'Afficher la provision';
      default :
        return 'Modele Option inconnu';
    }
  }

}

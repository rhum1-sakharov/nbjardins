import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevisOption, MODELE_OPTION, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../../../../services/announces/devis/devis-announces.service';
import {DevisHttpService} from '../../../../../../services/http/devis-http.service';
import {switchMap} from 'rxjs/operators';
import {DevisOptionUtils} from '../../../../../../services/utils/devis/options/devis-option-utils';
import {DesignerAnnouncesService} from '../../../../../../services/announces/devis/designer/designer-announces.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialog !: Subscription;
  devisOptionList !: MDevisOption[];


  doUtils = DevisOptionUtils;

  constructor(private devisAnnounceSvc: DevisAnnouncesService,
              private designerAnnounceSvc:DesignerAnnouncesService,
              private devisHttpSvc: DevisHttpService,) {
    super();
  }

  ngOnInit(): void {

    this.openDialogSubscription();
  }

  openDialogSubscription() {
    this.subOpenDialog = this.designerAnnounceSvc.openDialogDevisOptions$.pipe(
      switchMap(response => this.devisHttpSvc.findOptionsDevis(response.idDevis))
    )
      .subscribe((response: any) => {
        this.displayDialog = true;
        this.devisOptionList = response.data.devisOptionFindByIdDevis;
      });
  }

  saveDevisOptions() {

    this.devisHttpSvc.saveDevisOptionList(this.devisOptionList)
      .subscribe((response: any) => {

        this.devisAnnounceSvc.announceDevisOptionListUpdated(this.devisOptionList);
        this.close();
      });

  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialog);
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

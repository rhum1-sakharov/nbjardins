import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../services/announces/devis/devis-announces.service';
import {MDevis, MDevisOption, ObservableUtils, TYPE_FIELD} from 'rhum1-sakharov-core-lib';
import {filter, switchMap} from 'rxjs/operators';
import {DevisHttpService} from '../../../services/http/devis-http.service';
import {MODELE_OPTION} from '../../../../../../core-lib/dist/core-lib';
import {DevisOptionUtils} from '../../../services/utils/devis/options/devis-option-utils';
import {
  DesignerAnnouncesService,
  OpenDialogBlockArtisanSupplier,
  OpenDialogBlockClientSupplier
} from '../../../services/announces/devis/designer/designer-announces.service';

@Component({
  selector: 'app-designer',
  templateUrl: './designer.component.html',
  styleUrls: ['./designer.component.scss']
})
export class DesignerComponent implements OnInit, OnDestroy {

  subDevisSelected !: Subscription;
  subDevisRemoved !: Subscription;
  subDevisOptionListUpdated !: Subscription;
  subBlockClientUpdated !: Subscription;
  subBlockArtisanUpdated !: Subscription;

  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  readonly mo = MODELE_OPTION;
  readonly tf = TYPE_FIELD;
  doUtils = DevisOptionUtils;

  constructor(private devisAnnounceSvc: DevisAnnouncesService,
              private designerAnnounceSvc: DesignerAnnouncesService,
              private devisHttpSvc: DevisHttpService) {
  }

  ngOnInit(): void {

    this.devisSelectedSubscription();

    this.devisRemovedSubscription();

    this.devisOptionListUpdatedSubscription();

    this.blockArtisanUpdatedSubscription();

    this.blockClientUpdatedSubscription();

  }

  updateDevis(property: string, event: any) {
    this.devis[property] = event;
    this.devisAnnounceSvc.announceDevisUpdated(this.devis);
  }

  blockClientUpdatedSubscription() {
    this.subBlockClientUpdated = this.designerAnnounceSvc.blockClientUpdated$
      .subscribe(response => {
        this.devis = response;
        this.devisAnnounceSvc.announceDevisUpdated(this.devis);
      });
  }

  blockArtisanUpdatedSubscription() {
    this.subBlockArtisanUpdated = this.designerAnnounceSvc.blockArtisanUpdated$
      .subscribe(response => {
        this.devis = response;
        this.devisAnnounceSvc.announceDevisUpdated(this.devis);
      });
  }

  devisOptionListUpdatedSubscription() {
    this.subDevisOptionListUpdated = this.devisAnnounceSvc.devisOptionListUpdated$
      .subscribe(response => this.devisOptionList = response);
  }

  devisRemovedSubscription() {

    this.subDevisRemoved = this.devisAnnounceSvc.devisRemoved$.pipe(
      filter((response: any) => this.devis && response && response.id === this.devis.id)
    ).subscribe(response => {
      this.devis = new MDevis();
      this.devisOptionList = [];
    });

  }

  devisSelectedSubscription() {

    this.subDevisSelected = this.devisAnnounceSvc.devisSelected$.pipe(
      filter((response: any) => response && response.id),
      switchMap(response => this.devisHttpSvc.findDevis(response.id))
    )
      .subscribe((response: any) => {
        this.devis = response.data.devisFindById;
        this.devisOptionList = response.data.devisOptionFindByIdDevis;
        this.devisAnnounceSvc.announceDevisUpdated(this.devis);
      });
  }


  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subDevisRemoved);
    ObservableUtils.unsubscribe(this.subDevisSelected);
    ObservableUtils.unsubscribe(this.subDevisOptionListUpdated);
    ObservableUtils.unsubscribe(this.subBlockArtisanUpdated);
    ObservableUtils.unsubscribe(this.subBlockClientUpdated);
  }


  openDialogBlockArtisan() {
    const odba = new OpenDialogBlockArtisanSupplier(this.devis, this.devisOptionList);
    this.designerAnnounceSvc.announceOpenDialogBlockArtisan(odba);
  }

  openDialogBlockClient() {
    const odbc = new OpenDialogBlockClientSupplier(this.devis, this.devisOptionList);
    this.designerAnnounceSvc.announceOpenDialogBlockClient(odbc);
  }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {
  CollectionUtils,
  DateUtils,
  KEY_USER,
  LocalstorageService,
  MDevis,
  MSG_KEY,
  MSG_SEVERITY,
  ObservableUtils,
  ResponsiveUtils,
  ToasterService
} from 'rhum1-sakharov-core-lib';
import {DevisAnnouncesService, OpenDialogCreateDevisSupplier,} from '../../../../services/announces/devis/devis-announces.service';
import {ConfirmationService} from 'primeng/api';
import {DevisHttpService} from '../../../../services/http/devis-http.service';
import {
  DesignerAnnouncesService,
  OpenDialogDevisOptionsSupplier
} from '../../../../services/announces/devis/designer/designer-announces.service';

@Component({
  selector: 'app-a-traiter',
  templateUrl: './a-traiter.component.html',
  styleUrls: ['./a-traiter.component.scss']
})
export class ATraiterComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;
  subCloseDialogDevis !: Subscription;
  subDevisUpdated !: Subscription;

  responsiveUtils = ResponsiveUtils;
  dateUtils = DateUtils;

  numeroDevisWidth = 170;
  clientWidth = 220;
  depuisWidth = 110;

  devisList !: MDevis[];
  selectedDevis !: MDevis;
  devisToSave !: MDevis;

  constructor(private route: ActivatedRoute,
              private confirmationService: ConfirmationService,
              private devisAnnounceSvc: DevisAnnouncesService,
              private designerAnnouceSvc: DesignerAnnouncesService,
              private devisHttpSvc: DevisHttpService,
              private toastSvc: ToasterService,
              private ls: LocalstorageService) {
  }

  ngOnInit(): void {
    this.routeSubscription();

    this.devisCreatedSubscription();

    this.devisUpdatedSubscription();

  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {
      this.devisList = data.devisATraiterSupplier.data.devisFindByEmailArtisanAndStatut;

      if (!CollectionUtils.isNoe(this.devisList)) {
        this.selectedDevis = this.devisList[0];
        this.devisAnnounceSvc.announceDevisSelected(this.selectedDevis);
      }

    });
  }

  devisUpdatedSubscription() {
    this.subDevisUpdated = this.devisAnnounceSvc.devisUpdated$.subscribe((response: any) => this.devisToSave = response);
  }

  devisCreatedSubscription() {
    this.subCloseDialogDevis = this.devisAnnounceSvc.devisCreated$
      .subscribe(response => {

        this.devisList = [...this.devisList];
        this.devisList.unshift(response);
        this.selectedDevis = response;
        this.devisAnnounceSvc.announceDevisSelected(this.selectedDevis);
      });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
    ObservableUtils.unsubscribe(this.subCloseDialogDevis);
    ObservableUtils.unsubscribe(this.subDevisUpdated);
  }

  aTraiterSince(isoDate: string) {

    const dateATraiter = DateUtils.getDateFromIso(isoDate);
    const now = new Date();
    const nbDays = DateUtils.getNbDays(now, dateATraiter);

    let display = '';
    switch (nbDays) {
      case 0:
        display = `aujourd'hui`;
        break;
      case 1:
        display = `1 jour.`;
        break;
      default:
        display = `${nbDays} jours`;
        break;
    }

    return display;

  }

  openDialogCreateDevis() {

    const user = this.ls.getItem(KEY_USER);
    const odcd = new OpenDialogCreateDevisSupplier(user.email);
    this.devisAnnounceSvc.announceOpenDialogCreateDevis(odcd);
  }


  onRowSelect(event: any) {

    this.devisAnnounceSvc.announceDevisSelected(this.selectedDevis);
  }

  confirmRemoveDevis(devis: MDevis) {
    this.confirmationService.confirm({
      message: `Supprimer le devis ${devis.numeroDevis} ?`,
      accept: () => {
        this.devisHttpSvc.removeDevis(devis.id)
          .subscribe((response: any) => {
            this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, `Devis ${devis.numeroDevis} supprimé.`);

            this.devisList = [...this.devisList];
            this.devisList = this.devisList.filter(item => item.id !== devis.id);

            if (this.selectedDevis && this.selectedDevis.id === devis.id) {
              this.selectedDevis = new MDevis();
              this.devisAnnounceSvc.announceDevisSelected(this.selectedDevis);
            }

            this.devisAnnounceSvc.announceDevisRemoved(devis);
          });
      }
    })
  }

  saveDevis() {

    this.devisHttpSvc.saveDevis(this.devisToSave)
      .subscribe((response: any) => this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, `Devis ${this.devisToSave.numeroDevis} enregistré avec succès.`));

  }

  openDialogOptionsDevis() {
    if (this.selectedDevis && this.selectedDevis.id) {
      const oddo = new OpenDialogDevisOptionsSupplier(this.selectedDevis.id);
      this.designerAnnouceSvc.announceOpenDialogDevisOptions(oddo);
    }

  }
}

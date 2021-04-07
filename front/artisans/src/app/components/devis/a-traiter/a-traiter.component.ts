import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {
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
import {DevisAnnouncesService, OpenDialogCreateDevisSupplier} from '../../../services/announces/devis-announces.service';
import {ConfirmationService} from 'primeng/api';
import {DevisHttpService} from '../../../services/http/devis-http.service';

@Component({
  selector: 'app-a-traiter',
  templateUrl: './a-traiter.component.html',
  styleUrls: ['./a-traiter.component.scss']
})
export class ATraiterComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;
  subCloseDialogDevis !: Subscription;

  responsiveUtils = ResponsiveUtils;
  dateUtils = DateUtils;

  numeroDevisWidth = 170;
  clientWidth = 250;
  depuisWidth = 120;

  devisList !: MDevis[];
  selectedDevis !: MDevis;

  constructor(private route: ActivatedRoute,
              private confirmationService: ConfirmationService,
              private devisAnnounceSvc: DevisAnnouncesService,
              private devisHttpSvc: DevisHttpService,
              private toastSvc: ToasterService,
              private ls: LocalstorageService) {
  }

  ngOnInit(): void {
    this.routeSubscription();

    this.closeDialogCreateDevisSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisList = data.devisATraiterSupplier.data.devisFindByEmailArtisanAndStatut);
  }

  closeDialogCreateDevisSubscription() {
    this.subCloseDialogDevis = this.devisAnnounceSvc.closeDialogCreateDevis$
      .subscribe(response => {
        console.log(response);
        this.devisList = [...this.devisList];
        this.devisList.unshift(response);
      });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
    ObservableUtils.unsubscribe(this.subCloseDialogDevis);
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
    console.log('onRowSelect', this.selectedDevis);
  }

  confirmRemoveDevis(devis: MDevis) {
    this.confirmationService.confirm({
      message: `Supprimer le devis ${devis.numeroDevis} ?`,
      accept: () => {
        this.devisHttpSvc.removeDevis(devis.id)
          .subscribe((response: any) => this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, `Devis ${devis.numeroDevis} supprimé.`));
      }
    })
  }
}

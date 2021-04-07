import {Component, OnDestroy, OnInit} from '@angular/core';
import {MClient, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../../../services/announces/devis-announces.service';
import {switchMap} from 'rxjs/operators';
import {ClientsHttpService} from '../../../../../services/http/clients-http.service';
import {DevisHttpService} from '../../../../../services/http/devis-http.service';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialog !: Subscription;
  clientList !: MClient[];
  selectedClient !: MClient | null;
  emailArtisan !: string;

  constructor(private devisAnnounceSvc: DevisAnnouncesService,
              private devisHttpSvc: DevisHttpService,
              private clientHttpSvc: ClientsHttpService) {
    super();
  }

  ngOnInit(): void {
    this.openDialogSubscription();
  }

  openDialogSubscription() {
    this.subOpenDialog = this.devisAnnounceSvc.openDialogCreateDevis$
      .pipe(
        switchMap((response) => {
          this.selectedClient=null;
          this.emailArtisan = response.emailArtisan;
          return this.clientHttpSvc.findByEmailArtisan(this.emailArtisan);
        })
      ).subscribe(
        (
          response: any
        ) => {
          this.clientList = response.data.clientFindByEmailArtisan;
          this.displayDialog = true;
        }
      )
    ;
  }

  ngOnDestroy()
    :
    void {
    ObservableUtils.unsubscribe(this.subOpenDialog);
  }

  createDevisAtraiter() {

    let idClient=this.selectedClient ? this.selectedClient.id:null;

    this.devisHttpSvc.createDevisATraiter(this.emailArtisan,idClient);
  }
}

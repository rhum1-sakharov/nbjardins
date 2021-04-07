import {Component, OnDestroy, OnInit} from '@angular/core';
import {MClient, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../../../services/announces/devis-announces.service';
import {switchMap} from 'rxjs/operators';
import {ClientsHttpService} from '../../../../../services/http/clients-http.service';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialog !: Subscription;
  clientList !: MClient[];
  selectedClient !: MClient;

  constructor(private devisAnnounceSvc: DevisAnnouncesService, private clientHttpSvc: ClientsHttpService) {
    super();
  }

  ngOnInit(): void {
    this.openDialogSubscription();
  }

  openDialogSubscription() {
    this.subOpenDialog = this.devisAnnounceSvc.openDialogCreateDevis$
      .pipe(
        switchMap((response) => this.clientHttpSvc.findByEmailArtisan(response.emailArtisan))
      )
      .subscribe((response: any) => {
        this.clientList = response.data.clientFindByEmailArtisan;
        this.displayDialog = true;
      });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialog);
  }

  createDevisAtraiter(){

  }
}

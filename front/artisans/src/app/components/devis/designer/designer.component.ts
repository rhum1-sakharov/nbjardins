import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../services/announces/devis-announces.service';
import {MDevis, ObservableUtils} from 'rhum1-sakharov-core-lib';
import {filter, switchMap} from 'rxjs/operators';
import {DevisHttpService} from '../../../services/http/devis-http.service';

@Component({
  selector: 'app-designer',
  templateUrl: './designer.component.html',
  styleUrls: ['./designer.component.scss']
})
export class DesignerComponent implements OnInit, OnDestroy {

  subDevisSelected !: Subscription;
  subDevisRemoved !: Subscription;
  devis !: MDevis | null;


  constructor(private devisAnnounceSvc: DevisAnnouncesService, private devisHttpSvc: DevisHttpService) {
  }

  ngOnInit(): void {

    this.devisSelectedSubscription();

    this.devisRemovedSubscription();

  }

  devisRemovedSubscription() {

    this.subDevisRemoved = this.devisAnnounceSvc.devisRemoved$.pipe(
      filter((response: any) => this.devis && response && response.id === this.devis.id)
    ).subscribe(response => this.devis = null);

  }

  devisSelectedSubscription() {

    this.subDevisSelected = this.devisAnnounceSvc.devisSelected$.pipe(
      filter((response: any) => response && response.id),
      switchMap(response => this.devisHttpSvc.findDevis(response.id))
    )
      .subscribe((response: any) => {
        this.devis = response.data.devisFindById;
      });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subDevisRemoved);
    ObservableUtils.unsubscribe(this.subDevisSelected);
  }


}

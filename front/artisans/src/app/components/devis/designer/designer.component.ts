import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../services/announces/devis-announces.service';
import {MDevis} from 'rhum1-sakharov-core-lib';
import {switchMap} from 'rxjs/operators';
import {DevisHttpService} from '../../../services/http/devis-http.service';

@Component({
  selector: 'app-designer',
  templateUrl: './designer.component.html',
  styleUrls: ['./designer.component.scss']
})
export class DesignerComponent implements OnInit {

  subDevisSelected !: Subscription;
  devis !: MDevis;

  constructor(private devisAnnounceSvc: DevisAnnouncesService, private devisHttpSvc: DevisHttpService) {
  }

  ngOnInit(): void {

    this.devisSelectedSubscription();

  }

  devisSelectedSubscription() {
    this.subDevisSelected = this.devisAnnounceSvc.devisSelected$.pipe(
      switchMap(response => this.devisHttpSvc.findDevis(response.id))
    )
      .subscribe((response: any) => {
        this.devis = response.data.devisFindById;
      });
  }


}

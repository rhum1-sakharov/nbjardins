import {Component, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {DevisAnnouncesService} from '../../../services/announces/devis-announces.service';
import {MDevis} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-designer',
  templateUrl: './designer.component.html',
  styleUrls: ['./designer.component.scss']
})
export class DesignerComponent implements OnInit {

  subDevisSelected !: Subscription;
  devis !: MDevis;

  constructor(private devisAnnounceSvc: DevisAnnouncesService) {
  }

  ngOnInit(): void {

    this.devisSelectedSubscription();

  }

  devisSelectedSubscription() {
    this.subDevisSelected = this.devisAnnounceSvc.devisSelected$
      .subscribe(response => {
        this.devis = response;
      });
  }


}

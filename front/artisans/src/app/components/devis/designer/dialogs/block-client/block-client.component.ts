import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevis, MDevisOption, ObservableUtils, RvlDialog} from '../../../../../../../../core-lib/dist/core-lib';
import {Subscription} from 'rxjs';
import {DesignerAnnouncesService} from '../../../../../services/announces/devis/designer/designer-announces.service';

@Component({
  selector: 'app-block-client',
  templateUrl: './block-client.component.html',
  styleUrls: ['./block-client.component.scss']
})
export class BlockClientComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialogBlockClient !: Subscription;
  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  constructor(
              private designerAnnounceSvc:DesignerAnnouncesService
  ) {
    super();
  }

  ngOnInit(): void {

    this.openDialogBlockClientSubscription();
  }

  openDialogBlockClientSubscription() {
    this.subOpenDialogBlockClient = this.designerAnnounceSvc.openDialogBlockClient$.subscribe(response => {
      this.displayDialog = true;
      this.devis = response.devis;
      this.devisOptionList = response.devisOptionList;
    });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialogBlockClient);
  }

}

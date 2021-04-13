import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevis, MDevisOption, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DesignerAnnouncesService} from '../../../../../services/announces/devis/designer/designer-announces.service';

@Component({
  selector: 'app-block-artisan',
  templateUrl: './block-artisan.component.html',
  styleUrls: ['./block-artisan.component.scss']
})
export class BlockArtisanComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialogBlockArtisan !: Subscription;
  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  constructor(private designerAnnounceSvc:DesignerAnnouncesService) {
    super();
  }

  ngOnInit(): void {

    this.openDialogBlockArtisanSubscription();
  }

  openDialogBlockArtisanSubscription() {
    this.subOpenDialogBlockArtisan = this.designerAnnounceSvc.openDialogBlockArtisan$.subscribe(response => {
      this.displayDialog = true;
      this.devis = response.devis;
      this.devisOptionList = response.devisOptionList;
    });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialogBlockArtisan);
  }

}

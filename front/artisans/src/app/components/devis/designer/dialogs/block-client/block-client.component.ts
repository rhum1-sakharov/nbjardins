import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevis, MDevisOption, ObservableUtils, RvlDialog} from '../../../../../../../../core-lib/dist/core-lib';
import {Subscription} from 'rxjs';
import {DesignerAnnouncesService} from '../../../../../services/announces/devis/designer/designer-announces.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {DevisAnnouncesService} from '../../../../../services/announces/devis/devis-announces.service';

@Component({
  selector: 'app-block-client',
  templateUrl: './block-client.component.html',
  styleUrls: ['./block-client.component.scss']
})
export class BlockClientComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialogBlockClient !: Subscription;
  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  form !: FormGroup;


  constructor(
    private designerAnnounceSvc: DesignerAnnouncesService,
    private devisAnnounceSvc:DevisAnnouncesService

  ) {
    super();
  }

  ngOnInit(): void {


    this.openDialogBlockClientSubscription();
  }

  initForm(devis: MDevis) {

    this.form = new FormGroup({
      prenom: new FormControl(devis.clientPrenom),
      nom: new FormControl(devis.clientNom),
      adresse: new FormControl(devis.clientAdresse),
      ville: new FormControl(devis.clientVille),
      codePostal: new FormControl(devis.clientCodePostal),
      email: new FormControl(devis.clientEmail, [Validators.required, Validators.minLength(2), Validators.maxLength(50), Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
    })
  }

  openDialogBlockClientSubscription() {
    this.subOpenDialogBlockClient = this.designerAnnounceSvc.openDialogBlockClient$.subscribe(response => {
      this.displayDialog = true;
      this.devis = response.devis;
      this.devisOptionList = response.devisOptionList;

      this.initForm(this.devis);
    });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialogBlockClient);
  }

  updateClient() {
    console.log('updateClient');

    if (this.form.valid) {
      this.devis.clientPrenom = this.form.controls['prenom'].value;

      this.designerAnnounceSvc.announceBlockClientUpdated(this.devis);
    }
  }
}

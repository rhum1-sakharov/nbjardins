import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormUtils, MDevis, MDevisOption, ObservableUtils, RvlDialog, ValidatorsService} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DesignerAnnouncesService} from '../../../../../services/announces/devis/designer/designer-announces.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-block-client',
  templateUrl: './block-client.component.html',
  styleUrls: ['./block-client.component.scss']
})
export class BlockClientComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialogBlockClient !: Subscription;
  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  form  = new FormGroup({});


  constructor(
    private designerAnnounceSvc: DesignerAnnouncesService,
    public validatorsSvc: ValidatorsService
  ) {
    super();
  }

  ngOnInit(): void {

    this.initForm(null);

    this.openDialogBlockClientSubscription();
  }

  initForm(devis: MDevis | null) {

    this.form = new FormGroup({
      prenom: new FormControl(devis ? devis.clientPrenom :''),
      nom: new FormControl(devis ? devis.clientNom:''),
      adresse: new FormControl(devis ? devis.clientAdresse:''),
      ville: new FormControl(devis ? devis.clientVille:''),
      codePostal: new FormControl(devis ? devis.clientCodePostal:''),
      email: new FormControl(devis ? devis.clientEmail :'', [ Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
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

    FormUtils.validateAllFormFields(this.form);

    if (this.form.valid) {

      this.devis.clientPrenom = this.form.controls['prenom'].value;
      this.devis.clientNom = this.form.controls['nom'].value;
      this.devis.clientAdresse = this.form.controls['adresse'].value;
      this.devis.clientCodePostal = this.form.controls['codePostal'].value;
      this.devis.clientVille = this.form.controls['ville'].value;
      this.devis.clientEmail = this.form.controls['email'].value;

      this.designerAnnounceSvc.announceBlockClientUpdated(this.devis);
      this.close();
    }
  }
}

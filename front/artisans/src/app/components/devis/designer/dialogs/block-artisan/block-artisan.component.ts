import {Component, OnDestroy, OnInit} from '@angular/core';
import {MDevis, MDevisOption, ObservableUtils, RvlDialog} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';
import {DesignerAnnouncesService} from '../../../../../services/announces/devis/designer/designer-announces.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {FormUtils, ValidatorsService} from '../../../../../../../../core-lib/dist/core-lib';
import {DevisHttpService} from '../../../../../services/http/devis-http.service';

@Component({
  selector: 'app-block-artisan',
  templateUrl: './block-artisan.component.html',
  styleUrls: ['./block-artisan.component.scss']
})
export class BlockArtisanComponent extends RvlDialog implements OnInit, OnDestroy {

  subOpenDialogBlockArtisan !: Subscription;
  devis !: MDevis;
  devisOptionList !: MDevisOption[];

  form = new FormGroup({});

  constructor(private designerAnnounceSvc: DesignerAnnouncesService,
              private devisHttpSvc: DevisHttpService,
              public validatorsSvc: ValidatorsService) {
    super();
  }

  ngOnInit(): void {

    this.initForm(null);

    this.openDialogBlockArtisanSubscription();
  }

  initForm(devis: MDevis | null) {

    this.form = new FormGroup({
      siret: new FormControl(devis ? devis.artisanSiret : ''),
      societe: new FormControl(devis ? devis.artisanSociete : ''),
      adresse: new FormControl(devis ? devis.artisanAdresse : ''),
      ville: new FormControl(devis ? devis.artisanVille : ''),
      codePostal: new FormControl(devis ? devis.artisanCodePostal : ''),
      telephone: new FormControl(devis ? devis.artisanTelephone : ''),
      email: new FormControl(devis ? devis.artisanEmail : '', [Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      majArtisan: new FormControl(true, Validators.required)
    })
  }


  openDialogBlockArtisanSubscription() {
    this.subOpenDialogBlockArtisan = this.designerAnnounceSvc.openDialogBlockArtisan$.subscribe(response => {
      this.displayDialog = true;
      this.devis = response.devis;
      this.devisOptionList = response.devisOptionList;

      this.initForm(this.devis);
    });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subOpenDialogBlockArtisan);
  }

  updateArtisan() {

    FormUtils.validateAllFormFields(this.form);

    if (this.form.valid) {

      this.devis.artisanSiret = this.form.controls['siret'].value;
      this.devis.artisanSociete = this.form.controls['societe'].value;
      this.devis.artisanAdresse = this.form.controls['adresse'].value;
      this.devis.artisanCodePostal = this.form.controls['codePostal'].value;
      this.devis.artisanVille = this.form.controls['ville'].value;
      this.devis.artisanTelephone = this.form.controls['telephone'].value;
      this.devis.artisanEmail = this.form.controls['email'].value;

      if (this.form.controls['majArtisan'].value) {
        this.devisHttpSvc.artisanShareInfosDevis(this.devis).subscribe();
      }

      this.designerAnnounceSvc.announceBlockArtisanUpdated(this.devis);
      this.close();
    }
  }

}

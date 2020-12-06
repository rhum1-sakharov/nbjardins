import {Component, OnInit} from '@angular/core';
import {MVille} from "../core/models/m-ville";
import {VillesService} from "../core/services/metiers/villes.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ValidatorsService} from "../core/services/techniques/validators.service";
import {MSG_KEY, MSG_SEVERITY, ToasterService} from "../core/services/techniques/toaster.service";
import {MDemandeDeDevis} from "../core/models/m-demande-de-devis";
import {DemandeDeDevisService} from "../core/services/metiers/demande-de-devis.service";
import {TITLE_APP} from "../constants";
import {MPersonne} from "../core/models/m-personne";

@Component({
  selector: 'app-demande-de-devis',
  templateUrl: './demande-de-devis.component.html',
  styleUrls: ['./demande-de-devis.component.scss']
})
export class DemandeDeDevisComponent implements OnInit {

  villes: MVille[];

  form: FormGroup;

  constructor(private villesSvc: VillesService,
              public validatorsSvc: ValidatorsService,
              private demandeDeDevisSvc: DemandeDeDevisService,
              private toasterSvc: ToasterService) {
  }

  ngOnInit(): void {

    this.initForm();
  }

  initForm() {

    this.form = new FormGroup({
      nomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      prenomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      societeCtl: new FormControl(''),
      fonctionCtl: new FormControl(''),
      adresseCtl: new FormControl(''),
      villeCtl: new FormControl(''),
      telephoneCtl: new FormControl(''),
      emailCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50), Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      messageCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(800)]),
    });


  }

  search($event) {

    this.villesSvc.search($event.query).subscribe((response: any[]) => {
      this.villes = response.map(item => new MVille(item.nom, item.codesPostaux[0]));
    });
  }

  onSubmit() {
    if (this.form.valid) {

      const nom = this.form.get('nomCtl').value;
      const prenom = this.form.get('prenomCtl').value;
      const societe = this.form.get('societeCtl').value;
      const fonction = this.form.get('fonctionCtl').value;
      const adresse = this.form.get('adresseCtl').value;
      const ville = this.form.get('villeCtl').value ? this.form.get('villeCtl').value : null;
      const telephone = this.form.get('telephoneCtl').value;
      const email = this.form.get('emailCtl').value;
      const message = this.form.get('messageCtl').value;

      const asker = new MPersonne(nom, prenom, telephone, societe, "", adresse, ville, email);

      const demandeDeDevis = new MDemandeDeDevis(asker, null, "", message, TITLE_APP);
      this.demandeDeDevisSvc.send(demandeDeDevis).subscribe(response => {

        this.toasterSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, 'Votre demande a été envoyé avec succès. Je vous répondrai sous 48 heures.');
        this.form.reset();
      });


    } else {

      this.toasterSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.WARN, 'Veuillez renseigner les champs obligatoires.');
    }
  }

}

import {Component, OnInit} from '@angular/core';
import {MVille} from "../../../../front-lib/projects/core-lib/src/lib/models/m-ville";
import {VillesService} from "../core/services/metiers/villes.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ValidatorsService} from "../core/services/techniques/validators.service";
import {MSG_KEY, MSG_SEVERITY, ToasterService} from "../core/services/techniques/toaster.service";
import {MDemandeDeDevis} from "../../../../front-lib/projects/core-lib/src/lib/models/m-demande-de-devis";
import {DemandeDeDevisService} from "../core/services/metiers/demande-de-devis.service";
import {MPersonne} from "../../../../front-lib/projects/core-lib/src/lib/models/m-personne";
import {LoadingService} from "../core/services/techniques/loading.service";
import {faSync} from "@fortawesome/free-solid-svg-icons/faSync";
import {MClient} from '../../../../front-lib/projects/core-lib/src/lib/models/m-client';

@Component({
  selector: 'app-demande-de-devis',
  templateUrl: './demande-de-devis.component.html',
  styleUrls: ['./demande-de-devis.component.scss']
})
export class DemandeDeDevisComponent implements OnInit {

  villes: MVille[];

  form: FormGroup;
  faSync = faSync;

  constructor(private villesSvc: VillesService,
              public validatorsSvc: ValidatorsService,
              private demandeDeDevisSvc: DemandeDeDevisService,
              public loadingSvc: LoadingService,
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
      sujetCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
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
      const nomVille = ville ? ville.nom : null;
      const codePostal = ville ? ville.codePostal : null;
      const telephone = this.form.get('telephoneCtl').value;
      const email = this.form.get('emailCtl').value;
      const sujet = this.form.get('sujetCtl').value;
      const message = this.form.get('messageCtl').value;

      const personne = new MPersonne(nom, prenom, telephone, societe, fonction, adresse, nomVille, codePostal, email);
      const client = new MClient(personne);
      const demandeDeDevis = new MDemandeDeDevis(client, sujet, message);

      this.demandeDeDevisSvc.send(demandeDeDevis).subscribe(response => {

        this.toasterSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, 'Votre demande a été envoyé avec succès. Je vous répondrai sous 48 heures.');
        this.form.reset();
      });


    } else {

      this.toasterSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.WARN, 'Veuillez renseigner les champs obligatoires.');
    }
  }

}

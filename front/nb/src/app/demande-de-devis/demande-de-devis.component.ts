import {Component, OnInit} from '@angular/core';
import {
  LoadingService,
  MClient,
  MDemandeDeDevis,
  MPersonne,
  MSG_KEY,
  MSG_SEVERITY,
  MVille,
  ToasterService,
  ValidatorsService
} from 'rhum1-sakharov-core-lib';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {faSync} from '@fortawesome/free-solid-svg-icons/faSync';
import {VillesService} from '../core/services/metiers/villes.service';
import {DemandeDeDevisService} from '../core/services/metiers/demande-de-devis.service';

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

  initForm(): void {

    this.form = new FormGroup({
      nomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      prenomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      societeCtl: new FormControl(''),
      fonctionCtl: new FormControl(''),
      adresseCtl: new FormControl(''),
      villeCtl: new FormControl(''),
      telephoneCtl: new FormControl(''),
      emailCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50), Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      sujetCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      messageCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(800)]),
    });


  }

  search($event): void {

    this.villesSvc.search($event.query).subscribe((response: any[]) => {
      this.villes = response.map(item => new MVille(item.nom, item.codesPostaux[0]));
    });
  }

  onSubmit(): void {
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

import {Component, OnInit} from '@angular/core';
import {MVille} from "../core/models/m-ville";
import {VillesService} from "../core/services/villes.service";
import {faPenFancy} from "@fortawesome/free-solid-svg-icons/faPenFancy";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-demande-de-devis',
  templateUrl: './demande-de-devis.component.html',
  styleUrls: ['./demande-de-devis.component.scss']
})
export class DemandeDeDevisComponent implements OnInit {

  villes: MVille[];
  ville: MVille;
  faPenFancy = faPenFancy;

  devisMessage: string;

  form: FormGroup;

  constructor(private villesSvc: VillesService) {
  }

  ngOnInit(): void {

    this.initForm();
  }

  initForm() {
    this.form = new FormGroup({
      nomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      prenomCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]),
      societeCtl: new FormControl(''),
      adresseCtl: new FormControl(''),
      villeCtl: new FormControl(''),
      telephoneCtl: new FormControl(''),
      emailCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(50), Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
      messageCtl: new FormControl('', [Validators.required, Validators.minLength(2), Validators.maxLength(800)]),
    });
  }

  search($event) {

    this.villesSvc.search($event.query).subscribe(response => {
      this.villes = response.map(item => new MVille(item.nom, item.codesPostaux[0]));
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log('submitting');
    } else {
      console.log('form not valid');
    }
  }

  shouldShowRequiredError(ctlName: string) {

    const ctl = this.form.controls[ctlName];

    return ctl.invalid &&  ctl.touched && ctl.hasError('required');

  }

  shouldShowMinLengthError(ctlName: string) {

    const ctl = this.form.controls[ctlName];

    return ctl.invalid && ctl.touched && ctl.hasError('minlength');

  }

  shouldShowMaxLengthError(ctlName: string) {

    const ctl = this.form.controls[ctlName];

    return ctl.invalid &&  ctl.touched && ctl.hasError('maxlength');
  }

  shouldShowEmailPatternError(ctlName: string) {

    const ctl = this.form.controls[ctlName];
    return ctl.invalid && ctl.touched && ctl.hasError('pattern');
  }

  displayMinLengthError(ctlName: string, name: string) {
    const ctl = this.form.controls[ctlName];
    return `Le ${name} doit avoir au moins ${ctl.errors.minlength.requiredLength} caractères.`;
  }

  displayMaxLengthError(ctlName: string, name: string) {
    const ctl = this.form.controls[ctlName];
    return `Le ${name} ne doit pas dépasser ${ctl.errors.maxlength.requiredLength} caractères.`;
  }

  displayEmailPatternError(ctlName: string) {
    const ctl = this.form.controls[ctlName];
    return `La valeur saisie ne respecte pas le format d'une adresse email. Veillez à ne pas oublier le @.`;
  }

}

import {Injectable} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  constructor() { }

  shouldShowRequiredError(form: FormGroup, ctlName: string) {

    const ctl = form.controls[ctlName];

    return ctl.invalid && ctl.touched && ctl.hasError('required');

  }

  shouldShowMinLengthError(form: FormGroup, ctlName: string) {

    const ctl = form.controls[ctlName];

    return ctl.invalid && ctl.touched && ctl.hasError('minlength');

  }

  shouldShowMaxLengthError(form: FormGroup, ctlName: string) {

    const ctl = form.controls[ctlName];

    return ctl.invalid && ctl.touched && ctl.hasError('maxlength');
  }

  shouldShowEmailPatternError(form: FormGroup, ctlName: string) {

    const ctl = form.controls[ctlName];
    return ctl.invalid && ctl.touched && ctl.hasError('pattern');
  }

  displayMinLengthError(form: FormGroup, ctlName: string, name: string) {
    const ctl = form.controls[ctlName];
    return `Le ${name} doit avoir au moins ${ctl.errors.minlength.requiredLength} caractères.`;
  }

  displayMaxLengthError(form: FormGroup, ctlName: string, name: string) {
    const ctl = form.controls[ctlName];
    return `Le ${name} ne doit pas dépasser ${ctl.errors.maxlength.requiredLength} caractères.`;
  }

  displayEmailPatternError(form: FormGroup, ctlName: string) {
    const ctl = form.controls[ctlName];
    return `La valeur saisie ne respecte pas le format d'une adresse email. Veillez à ne pas oublier le @.`;
  }
}

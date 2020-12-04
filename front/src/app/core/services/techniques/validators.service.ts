import {Injectable} from '@angular/core';
import {AbstractControl} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  constructor() { }

  shouldShowRequiredError(ctl :AbstractControl) {

    return ctl.invalid && ctl.touched && ctl.hasError('required');

  }

  shouldShowMinLengthError(ctl :AbstractControl) {

    return ctl.invalid && ctl.touched && ctl.hasError('minlength');

  }

  shouldShowMaxLengthError(ctl :AbstractControl) {

    return ctl.invalid && ctl.touched && ctl.hasError('maxlength');
  }

  shouldShowEmailPatternError(ctl :AbstractControl) {


    return ctl.invalid && ctl.touched && ctl.hasError('pattern');
  }

  displayMinLengthError(ctl :AbstractControl, name: string) {

    return `Le ${name} doit avoir au moins ${ctl.errors.minlength.requiredLength} caractères.`;
  }

  displayMaxLengthError(ctl :AbstractControl, name: string) {

    return `Le ${name} ne doit pas dépasser ${ctl.errors.maxlength.requiredLength} caractères.`;
  }

  displayEmailPatternError(ctl :AbstractControl) {

    return `La valeur saisie ne respecte pas le format d'une adresse email. Veillez à ne pas oublier le @.`;
  }
}

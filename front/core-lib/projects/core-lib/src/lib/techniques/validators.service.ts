import {Injectable} from '@angular/core';
import {AbstractControl} from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  constructor() {
  }

  shouldShowRequiredError(ctl: AbstractControl): boolean {

    return ctl.invalid && ctl.touched && ctl.hasError('required');

  }

  shouldShowMinLengthError(ctl: AbstractControl): boolean {

    return ctl.invalid && ctl.touched && ctl.hasError('minlength');

  }

  shouldShowMaxLengthError(ctl: AbstractControl): boolean {

    return ctl.invalid && ctl.touched && ctl.hasError('maxlength');
  }

  shouldShowEmailPatternError(ctl: AbstractControl): boolean {


    return ctl.invalid && ctl.touched && ctl.hasError('pattern');
  }

  displayMinLengthError(ctl: AbstractControl, name: string): string {
    if (ctl.errors) {
      return `Le ${name} doit avoir au moins ${ctl.errors.minlength.requiredLength} caractères.`;
    }
    return '';
  }

  displayMaxLengthError(ctl: AbstractControl, name: string): string {

    if (ctl.errors) {
      return `Le ${name} ne doit pas dépasser ${ctl.errors.maxlength.requiredLength} caractères.`;
    }
    return '';

  }

  displayEmailPatternError(ctl: AbstractControl): string {

    return `La valeur saisie ne respecte pas le format d'une adresse email. Veillez à ne pas oublier le @.`;
  }
}

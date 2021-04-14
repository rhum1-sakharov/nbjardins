import {FormControl, FormGroup} from '@angular/forms';

export class FormUtils {

  /**
   * Lors d'un submit, on declenche les erreurs des champs mals renseignés
   * @param {FormGroup} formGroup
   */
  public static validateAllFormFields(formGroup: FormGroup) {


    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {

        control.markAsDirty({onlySelf: true});
        control.markAsTouched({onlySelf: true});

      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
        // this.cd.markForCheck();
        // console.log('control',control);
      }
    });
  }
}

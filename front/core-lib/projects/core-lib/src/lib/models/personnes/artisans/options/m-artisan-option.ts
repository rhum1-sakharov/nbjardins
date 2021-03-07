import {Model} from '../../../model';
import {MArtisan} from '../m-artisan';
import {MODELE_OPTION} from '../../../../enums/e-modele-option';

export class MArtisanOption extends Model {

  id !: string;
  artisan !: MArtisan;
  modeleOption!: MODELE_OPTION;
  actif !: boolean;

}

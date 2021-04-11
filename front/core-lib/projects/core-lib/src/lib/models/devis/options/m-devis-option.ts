import {Model} from '../../model';
import {MDevis} from '../m-devis';
import {MODELE_OPTION} from '../../../enums/e-modele-option';


export class MDevisOption extends Model {

  [propName: string]: MODELE_OPTION | boolean | MDevis | string;

  id !: string;
  devis !: MDevis;
  modeleOption !: MODELE_OPTION;
  actif !: boolean;


}

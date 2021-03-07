import {Model} from '../../model';
import {MPersonne} from '../m-personne';
import {MTaxe} from '../../referentiel/taxes/m-taxe';
import {MConditionReglement} from '../../referentiel/conditions/reglements/m-condition-reglement';

export class MArtisan extends Model {

  id !: string;
  personne!: MPersonne;
  logo: string = '';
  taxe !: MTaxe;
  conditionDeReglement !: MConditionReglement;
  provision: number = 0;
  siret: string = '';
  validiteDevisMois: number = 1;
  signature: string = '';


}

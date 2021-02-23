import {Model} from './model';
import {MPersonne} from './m-personne';

export class MArtisan  extends Model {

  personne!: MPersonne;
  prenom: string='';
  numeroTelephone: string='';
  societe: string='';
  fonction: string='';
  adresse: string='';
  ville: string='';
  codePostal:string='';
  email: string='';
  logo: string='';



}

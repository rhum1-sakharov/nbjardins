import {Model} from './model';

export class MPersonne extends Model {

  id !: string;
  nom: string = '';
  prenom: string = '';
  numeroTelephone: string = '';
  societe: string = '';
  fonction: string = '';
  adresse: string = '';
  ville: string = '';
  codePostal: string = '';
  email: string = '';


}

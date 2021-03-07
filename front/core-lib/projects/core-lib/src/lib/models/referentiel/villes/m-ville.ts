import {Model} from '../../model';

export class MVille extends Model{
  nom: string;
  codePostal: string;


  constructor(nom: string, codePostal: string) {
    super();
    this.nom = nom;
    this.codePostal = codePostal;
  }
}

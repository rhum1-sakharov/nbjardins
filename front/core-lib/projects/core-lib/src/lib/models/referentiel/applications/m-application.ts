import {Model} from '../../model';

export class MApplication  extends Model {

  nom: string;
  token: string;


  constructor(nom: string, token: string) {
    super();
    this.nom = nom;
    this.token = token;
  }
}

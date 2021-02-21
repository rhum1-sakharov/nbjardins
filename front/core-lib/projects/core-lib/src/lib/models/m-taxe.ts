import {Model} from './model';

export class MTaxe extends Model {

  id: string;
  nom: string;
  taux: number;


  constructor(id: string, nom: string, taux: number) {
    super();
    this.id = id;
    this.nom = nom;
    this.taux = taux;
  }
}

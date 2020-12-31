import {Model} from "./model";
import {MPersonne} from './m-personne';

export class MClient extends Model {

  personne: MPersonne;

  constructor(personne: MPersonne) {
    super();
    this.personne = personne;

  }


}

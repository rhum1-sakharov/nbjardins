import {MPersonne} from "./m-personne";
import {Model} from "./model";

export class MDemandeDeDevis extends Model{

  asker: MPersonne;
  sujet: string;
  message: string;


  constructor(asker: MPersonne, sujet: string, message: string) {
    super();
    this.asker = asker;
    this.sujet = sujet;
    this.message = message;
  }
}

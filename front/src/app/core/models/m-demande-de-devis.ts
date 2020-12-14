import {MPersonne} from "./m-personne";

export class MDemandeDeDevis {

  asker: MPersonne;
  sujet: string;
  message: string;


  constructor(asker: MPersonne, sujet: string, message: string) {
    this.asker = asker;
    this.sujet = sujet;
    this.message = message;
  }
}

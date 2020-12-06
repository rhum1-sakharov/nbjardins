import {MPersonne} from "./m-personne";

export class MDemandeDeDevis {

  asker: MPersonne;
  worker: MPersonne;
  sujet: string;
  message: string;
  application: string;


  constructor(asker: MPersonne, worker: MPersonne, sujet: string, message: string, application: string) {
    this.asker = asker;
    this.worker = worker;
    this.sujet = sujet;
    this.message = message;
    this.application = application;
  }
}

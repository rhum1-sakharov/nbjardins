import {Model} from '../model';
import {MClient} from '../personnes/clients/m-client';

export class MDemandeDeDevis extends Model{

  client: MClient;
  sujet: string;
  message: string;


  constructor(client: MClient, sujet: string, message: string) {
    super();
    this.client = client;
    this.sujet = sujet;
    this.message = message;
  }
}

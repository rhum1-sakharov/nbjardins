import {Model} from '../model';

export class MDevis extends Model {

  id !: string;
  numeroDevis!: string;
  dateATraiter!: Date;
  dateAbandon!: Date;
  dateRefuse!: Date;
  dateTraite!: Date;
  dateAccepte!: Date;
  clientNom!: string;
  clientPrenom!: string;
  clientSociete!: string;


}

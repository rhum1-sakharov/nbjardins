import {Model} from './model';

export class MPersonne  extends Model {

  nom: string;
  prenom: string;
  numeroTelephone: string;
  societe: string;
  fonction: string;
  adresse: string;
  ville: string;
  codePostal:string;
  email: string;


  constructor(nom: string, prenom: string, numeroTelephone: string, societe: string, fonction: string, adresse: string, ville: string,codePostal:string, email: string) {
    super();
    this.nom = nom;
    this.prenom = prenom;
    this.numeroTelephone = numeroTelephone;
    this.societe = societe;
    this.fonction = fonction;
    this.adresse = adresse;
    this.ville = ville;
    this.codePostal=codePostal;
    this.email = email;
  }
}

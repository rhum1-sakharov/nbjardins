import {MVille} from "./m-ville";

export class MPersonne {

  nom: string;
  prenom: string;
  numeroTelephone: string;
  societe: string;
  fonction: string;
  adresse: string;
  ville: MVille;
  email: string;


  constructor(nom: string, prenom: string, numeroTelephone: string, societe: string, fonction: string, adresse: string, ville: MVille, email: string) {
    this.nom = nom;
    this.prenom = prenom;
    this.numeroTelephone = numeroTelephone;
    this.societe = societe;
    this.fonction = fonction;
    this.adresse = adresse;
    this.ville = ville;
    this.email = email;
  }
}

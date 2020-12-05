import {MVille} from "./m-ville";

export class MDemandeDeDevis {

  nom: string;
  prenom: string;
  numeroTelephone: string;
  message: string;
  societe: string;
  fonction: string;
  adresse: string;
  ville: MVille;
  sujet: string;
  emailEmetteur: string;
  emailDestinataire: string;


  constructor(nom: string, prenom: string, numeroTelephone: string, message: string, societe: string, fonction: string, adresse: string, ville: MVille, sujet: string, emailEmetteur: string, emailDestinataire: string) {
    this.nom = nom;
    this.prenom = prenom;
    this.numeroTelephone = numeroTelephone;
    this.message = message;
    this.societe = societe;
    this.fonction = fonction;
    this.adresse = adresse;
    this.ville = ville;
    this.sujet = sujet;
    this.emailEmetteur = emailEmetteur;
    this.emailDestinataire = emailDestinataire;
  }
}

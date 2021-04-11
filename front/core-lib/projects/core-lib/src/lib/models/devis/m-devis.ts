import {Model} from '../model';
import {MArtisan} from '../personnes/artisans/m-artisan';
import {MClient} from '../personnes/clients/m-client';
import {STATUT_DEVIS} from '../../enums/e-statut-devis';


export class MDevis extends Model {

  [propName: string]: string | number | STATUT_DEVIS | MClient | MArtisan;

  id !: string;
  numeroDevis!: string;
  lieu !: string;
  statut !: STATUT_DEVIS;
  totalHT !: number;
  sujet !: string;
  remarque !: string;
  conditionDeReglement !: string;
  ordre !: string;
  banque !: string;
  iban !: string;
  rib !: string;
  provision !: number;
  validiteDevisMois !: number;


  dateATraiter!: string;
  dateAbandon!: string;
  dateRefuse!: string;
  dateTraite!: string;
  dateAccepte!: string;
  dateDevis !: string;

  client !: MClient;
  clientNom !: string;
  clientPrenom !: string;
  clientAdresse !: string;
  clientVille !: string;
  clientCodePostal !: string;
  clientTelephone !: string;
  clientEmail !: string;
  clientSignature !: string;
  clientSiret !: string;
  clientSociete !: string;
  clientFonction !: string;

  artisan !: MArtisan;
  artisanLogo !: string;
  artisanSiret !: string;
  artisanSociete !: string;
  artisanFonction !: string;
  artisanAdresse !: string;
  artisanVille !: string;
  artisanCodePostal !: string;
  artisanTelephone !: string;
  artisanEmail !: string;
  artisanSignature !: string;


}

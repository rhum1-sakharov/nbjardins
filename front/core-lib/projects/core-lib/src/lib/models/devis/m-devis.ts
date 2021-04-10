import {Model} from '../model';


export class MDevis extends Model {

  id !: string;
  numeroDevis!: string;
  lieu !: string;

  dateATraiter!: string;
  dateAbandon!: string;
  dateRefuse!: string;
  dateTraite!: string;
  dateAccepte!: string;
  dateDevis !: string;

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

import {Model} from '../model';

export class MDevis extends Model {

  id !: string;
  numeroDevis!: string;
  dateATraiter!: Date;
  dateAbandon!: Date;
  dateRefuse!: Date;
  dateTraite!: Date;
  dateAccepte!: Date;

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

  lieu !: string;

}

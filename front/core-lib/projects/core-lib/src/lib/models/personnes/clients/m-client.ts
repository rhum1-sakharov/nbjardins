import {Model} from '../../model';
import {MArtisan} from '../artisans/m-artisan';


export class MClient extends Model {

  artisan !: MArtisan;
  nom !: string;
  prenom !: string;
  adresse !: string;
  ville !: string;
  codePostal !: string;
  telephone !: string;
  email !: string;
  signature !: string;
  siret !: string;
  societe !: string;
  fonction !: string;


}

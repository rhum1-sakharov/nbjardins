import {Model} from '../../../model';
import {MArtisan} from '../m-artisan';

export class MArtisanBanque extends Model {

  id !: string;
  rib: string = '';
  iban: string = '';
  artisan!: MArtisan;
  banque !: string;
  prefere !: boolean;

}

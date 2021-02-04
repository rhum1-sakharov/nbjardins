import {AfterViewInit, ChangeDetectorRef, Component} from '@angular/core';
import {TOKEN_APP} from './constants';
import {faEnvelope} from '@fortawesome/free-regular-svg-icons/faEnvelope';
import {faMobileAlt} from '@fortawesome/free-solid-svg-icons';
import {faMapMarkerAlt} from '@fortawesome/free-solid-svg-icons/faMapMarkerAlt';
import {faAt} from '@fortawesome/free-solid-svg-icons/faAt';
import {faPenFancy} from '@fortawesome/free-solid-svg-icons/faPenFancy';
import {faCarrot} from '@fortawesome/free-solid-svg-icons/faCarrot';
import {faUserCheck} from '@fortawesome/free-solid-svg-icons/faUserCheck';
import {faBuilding} from '@fortawesome/free-solid-svg-icons/faBuilding';
import {faTasks} from '@fortawesome/free-solid-svg-icons/faTasks';
import {faAddressCard} from '@fortawesome/free-solid-svg-icons/faAddressCard';
import {faCity} from '@fortawesome/free-solid-svg-icons/faCity';
import {faCode} from '@fortawesome/free-solid-svg-icons/faCode';
import {faHome} from '@fortawesome/free-solid-svg-icons/faHome';
import {faFileInvoice} from '@fortawesome/free-solid-svg-icons/faFileInvoice';
import {MSG_KEY, MSG_POSITION} from 'rhum1-sakharov-core-lib';
import {VillesService} from './core/services/metiers/villes.service';


declare var ol: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {

  title = TOKEN_APP;

  faEnvelope = faEnvelope;
  faMobile = faMobileAlt;
  faMapMarkerAlt = faMapMarkerAlt;
  faAt = faAt;
  faPenFancy = faPenFancy;
  faCarrot = faCarrot;
  faFirstname = faUserCheck;
  faLastname = faUserCheck;
  faBuilding = faBuilding;
  faFunction = faTasks;
  faAddress = faAddressCard;
  faPostalCode = faCode;
  faCity = faCity;
  faHome = faHome;
  faFileInvoice = faFileInvoice;
  readonly ROOT_KEY = MSG_KEY.ROOT;
  readonly MSG_POSITION = MSG_POSITION.BOTTOM_CENTER;


  constructor(private cd: ChangeDetectorRef, private villesSvc: VillesService) {

  }

  ngOnInit(): void {


  }

  ngAfterViewInit(): void {

  }


}

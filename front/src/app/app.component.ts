import {AfterViewInit, ChangeDetectorRef, Component} from '@angular/core';
import {TITLE_APP} from "./constants";
import {faEnvelope} from "@fortawesome/free-regular-svg-icons/faEnvelope";
import {faMobileAlt} from "@fortawesome/free-solid-svg-icons";
import {faMapMarkerAlt} from "@fortawesome/free-solid-svg-icons/faMapMarkerAlt";
import {faAt} from "@fortawesome/free-solid-svg-icons/faAt";
import {faPenFancy} from "@fortawesome/free-solid-svg-icons/faPenFancy";
import {faCarrot} from "@fortawesome/free-solid-svg-icons/faCarrot";
import {faUserCheck} from "@fortawesome/free-solid-svg-icons/faUserCheck";
import {faBuilding} from "@fortawesome/free-solid-svg-icons/faBuilding";
import {faTasks} from "@fortawesome/free-solid-svg-icons/faTasks";
import {faAddressCard} from "@fortawesome/free-solid-svg-icons/faAddressCard";
import {faCity} from "@fortawesome/free-solid-svg-icons/faCity";
import {faCode} from "@fortawesome/free-solid-svg-icons/faCode";
import {VillesService} from "./core/services/villes.service";
import {MVille} from "./core/models/m-ville";

declare var ol: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {

  title = TITLE_APP;

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


  villes: MVille[];
  ville: MVille ;

  devisMessage: string;

  // Observer options.
  options = {
    root: null,
    rootMargin: '0px',
    threshold: 0.7,
  };

  constructor(private cd: ChangeDetectorRef, private villesSvc: VillesService) {

  }

  ngOnInit() {


  }

  ngAfterViewInit(): void {

  }

  search($event) {

    this.villesSvc.search($event.query).subscribe(response => {
      this.villes = response.map(item => new MVille(item.nom, item.codesPostaux[0]));
    });
  }


}

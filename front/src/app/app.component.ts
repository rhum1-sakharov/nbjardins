import {Component} from '@angular/core';
import {TITLE_APP} from "./constants";
import {faEnvelope} from "@fortawesome/free-regular-svg-icons/faEnvelope";
import {faPhone} from "@fortawesome/free-solid-svg-icons/faPhone";
import {faMobileAlt} from "@fortawesome/free-solid-svg-icons";
import {faMapMarkerAlt} from "@fortawesome/free-solid-svg-icons/faMapMarkerAlt";

declare var ol: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = TITLE_APP;

  faEnvelope = faEnvelope;
  faMobile = faMobileAlt;
  faMapMarkerAlt = faMapMarkerAlt;

  window;


  ngOnInit() {
    this.window = window;
    console.log(window.innerWidth);
  }


}

import {AfterViewInit, ChangeDetectorRef, Component} from '@angular/core';
import {TITLE_APP} from "./constants";
import {faEnvelope} from "@fortawesome/free-regular-svg-icons/faEnvelope";
import {faPhone} from "@fortawesome/free-solid-svg-icons/faPhone";
import {faMobileAlt} from "@fortawesome/free-solid-svg-icons";
import {faMapMarkerAlt} from "@fortawesome/free-solid-svg-icons/faMapMarkerAlt";
import {faAt} from "@fortawesome/free-solid-svg-icons/faAt";
import {faPenFancy} from "@fortawesome/free-solid-svg-icons/faPenFancy";
import {faCarrot} from "@fortawesome/free-solid-svg-icons/faCarrot";

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

  intersectionPrestation = false;

  devisMessage: string;

  // Observer options.
  options = {
    root: null,
    rootMargin: '0px',
    threshold: 0.7,
  };

  constructor(private cd: ChangeDetectorRef) {

  }

  ngOnInit() {


  }

  ngAfterViewInit(): void {

  }

  createObserver(idElement: string) {
    const prestations = document.getElementById(idElement);
    let prevRatio = 0;
    let increasingColor = "rgba(40, 40, 190, ratio)";
    let decreasingColor = "rgba(190, 40, 40, ratio)";
    console.log('onInit', prestations);


    // Callback function executed during observe.
    const callback = function (entries, observer) {
// Target the first entry available.
      let entry = entries[0];


      if (entry.intersectionRatio > prevRatio) {
        entry.target.style.backgroundColor = increasingColor.replace("ratio", entry.intersectionRatio);
      } else {
        entry.target.style.backgroundColor = decreasingColor.replace("ratio", entry.intersectionRatio);
      }

      prevRatio = entry.intersectionRatio;

    };

// Construct Intersection Observer.
    const observer = new IntersectionObserver(callback, this.options);

// Observe if element is present.
    if (prestations) {
      observer.observe(prestations);
    }
  }


}

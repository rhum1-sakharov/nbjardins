import { Component } from '@angular/core';
import {TITLE_APP} from "./constants";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  title = TITLE_APP;

}

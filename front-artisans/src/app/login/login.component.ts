import {Component, OnInit} from '@angular/core';
import {OAUTH_GOOGLE_CONNECTION_URL} from "../constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  readonly OAUTH_GOOGLE_CONNECTION_URL = OAUTH_GOOGLE_CONNECTION_URL;
  constructor() { }

  ngOnInit(): void {
  }

}

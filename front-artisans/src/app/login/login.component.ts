import {Component, OnInit} from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

 readonly URL_INITIATE_GOOGLE_OAUTH_ARTISAN = `api/authorization/initiate-google-oauth?typePersonne=ARTISAN`;

  constructor() { }

  ngOnInit(): void {
  }

}

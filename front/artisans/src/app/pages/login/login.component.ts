import {Component, OnInit} from '@angular/core';
import {AuthService} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {



  constructor(public authSvc: AuthService) { }

  ngOnInit(): void {
  }

}

import {Component, OnInit} from '@angular/core';
import {AuthService, KEY_USER, LocalstorageService, Utilisateur} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user = new Utilisateur();

  constructor(public authSvc: AuthService, public ls: LocalstorageService) {
  }

  ngOnInit(): void {

    this.authSvc.user$.subscribe((response: any) => {
      const lsUser = this.ls.getItem(KEY_USER);
      this.user = lsUser ? lsUser : new Utilisateur();
    });

  }

}

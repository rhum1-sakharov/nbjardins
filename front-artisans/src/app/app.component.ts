import {Component, OnInit} from '@angular/core';
import {AuthService} from './login/auth.service';
import {HttpService} from 'rhum1-sakharov-core-lib';
import {HttpParams} from '@angular/common/http';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'front-artisans';

  constructor(public authSvc: AuthService, private httpSvc: HttpService) {
  }

  ngOnInit(): void {

    this.authSvc.startUserSession();

  }

  displayArtisan(): void {
    const params = new HttpParams();
    this.httpSvc.get(`api/artisans`, params).subscribe();
  }
}

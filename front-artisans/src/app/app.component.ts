import {Component, OnInit} from '@angular/core';
import {AuthService} from './login/auth.service';
import {HttpParams} from '@angular/common/http';
import {MSG_KEY, MSG_POSITION} from 'rhum1-sakharov-core-lib';
import {HttpService} from './module-core/http.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'front-artisans';
  readonly ROOT_KEY = MSG_KEY.ROOT;
  readonly MSG_POSITION = MSG_POSITION.BOTTOM_CENTER;

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

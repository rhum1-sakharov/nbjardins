import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../core-lib/projects/core-lib/src/lib/techniques/auth.service';
import {HttpService, MSG_KEY, MSG_POSITION} from 'rhum1-sakharov-core-lib';


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
    this.httpSvc.get(`api/artisans`).subscribe();
  }
}

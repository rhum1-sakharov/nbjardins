import {Component, OnInit} from '@angular/core';
import {AuthService} from './login/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'front-artisans';

  constructor(private authSvc: AuthService){}

  ngOnInit(): void {


      this.authSvc.startUserSession();

  }
}

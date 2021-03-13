import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.scss']
})
export class TopMenuComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  callMe() {
      window.location.href='tel:06 51 15 88 92';
      console.log('callMe');
  }
}

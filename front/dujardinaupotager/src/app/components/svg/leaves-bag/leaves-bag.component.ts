import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-leaves-bag',
  templateUrl: './leaves-bag.component.html',
  styleUrls: ['./leaves-bag.component.scss']
})
export class LeavesBagComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  log(){
    const path = document.querySelector('path');
    console.log(path.getTotalLength());
  }

}

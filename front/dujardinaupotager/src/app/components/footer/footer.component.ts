import {Component, OnInit} from '@angular/core';
import {timer} from 'rxjs';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  comeon = false;

  constructor() {
  }

  ngOnInit(): void {

    const sourceStart$ = timer(6000);
     const sourceStop$ = timer(18000);

    sourceStart$.subscribe(response => this.comeon = true);
    sourceStop$.subscribe(response => this.comeon = false);

  }

}

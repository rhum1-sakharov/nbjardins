import {Component, OnInit} from '@angular/core';
import {faCarrot} from "@fortawesome/free-solid-svg-icons/faCarrot";

@Component({
  selector: 'app-accueil',
  templateUrl: './accueil.component.html',
  styleUrls: ['./accueil.component.scss']
})
export class AccueilComponent implements OnInit {


  faCarrot = faCarrot;



  constructor() { }

  ngOnInit(): void {
  }

}

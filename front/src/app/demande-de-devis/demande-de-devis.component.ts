import {Component, OnInit} from '@angular/core';
import {MVille} from "../core/models/m-ville";
import {VillesService} from "../core/services/villes.service";
import {faPenFancy} from "@fortawesome/free-solid-svg-icons/faPenFancy";

@Component({
  selector: 'app-demande-de-devis',
  templateUrl: './demande-de-devis.component.html',
  styleUrls: ['./demande-de-devis.component.scss']
})
export class DemandeDeDevisComponent implements OnInit {

  villes: MVille[];
  ville: MVille ;
  faPenFancy = faPenFancy;


  devisMessage: string;

  constructor(private villesSvc : VillesService) { }

  ngOnInit(): void {
  }

  search($event) {

    this.villesSvc.search($event.query).subscribe(response => {
      this.villes = response.map(item => new MVille(item.nom, item.codesPostaux[0]));
    });
  }

}

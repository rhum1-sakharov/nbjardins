import {Component, OnInit} from '@angular/core';
import {DevisAnnouncesService} from '../../services/announces/devis/devis-announces.service';

@Component({
  selector: 'app-top-menu',
  templateUrl: './top-menu.component.html',
  styleUrls: ['./top-menu.component.scss']
})
export class TopMenuComponent implements OnInit {

  constructor(private devisAnnounceSvc:DevisAnnouncesService) { }

  ngOnInit(): void {
  }

  devisSelected() {
    this.devisAnnounceSvc.announceDevisMenuSelected();
  }
}

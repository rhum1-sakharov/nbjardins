import {Component, Input, OnInit} from '@angular/core';
import {MArtisan, MArtisanBanque} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-preview-devis',
  templateUrl: './preview-devis.component.html',
  styleUrls: ['./preview-devis.component.scss']
})
export class PreviewDevisComponent implements OnInit {

  @Input() artisan !: MArtisan;
  @Input() artisanBanque !: MArtisanBanque;

  previewDate = new Date();

  constructor() {
  }

  ngOnInit(): void {
  }

}

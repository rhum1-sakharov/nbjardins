import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MDevis, MDevisOption, MODELE_OPTION, TYPE_FIELD} from 'rhum1-sakharov-core-lib';
import {DevisOptionUtils} from '../../../../services/utils/devis/options/devis-option-utils';
import {DevisAnnouncesService} from '../../../../services/announces/devis/devis-announces.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit, OnDestroy {


  @Input() devisOptionList !:MDevisOption[];
  @Input() devis !:MDevis;


  readonly mo = MODELE_OPTION;
  readonly tf = TYPE_FIELD;
  readonly doUtils = DevisOptionUtils;

  constructor(private devisAnnounceSvc: DevisAnnouncesService,) { }

  ngOnInit(): void {
  }



  ngOnDestroy(): void {

  }

}

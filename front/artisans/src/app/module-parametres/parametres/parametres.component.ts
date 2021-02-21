import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {MConditionReglement, MTaxe, UtilsService} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-parametres',
  templateUrl: './parametres.component.html',
  styleUrls: ['./parametres.component.scss']
})
export class ParametresComponent implements OnInit, OnDestroy {

  taxes: MTaxe[] = [];
  conditionsReglements: MConditionReglement[] = [];
  error: any;
  subRoute: Subscription= new Subscription();

  constructor(private route: ActivatedRoute, private utils: UtilsService) {
  }

  ngOnInit() {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.taxes = data.parametresSupplier.data.allTaxes;
      this.conditionsReglements = data.parametresSupplier.data.allConditionsReglements;
    });
  }

  ngOnDestroy(): void {
    this.utils.unsubscribe(this.subRoute);
  }

}

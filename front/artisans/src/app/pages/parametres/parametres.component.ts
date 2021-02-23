import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {MArtisan, MConditionReglement, MTaxe, UtilsService} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-parametres',
  templateUrl: './parametres.component.html',
  styleUrls: ['./parametres.component.scss']
})
export class ParametresComponent implements OnInit, OnDestroy {

  taxes: MTaxe[] = [];
  conditionsReglements: MConditionReglement[] = [];
  artisan !: MArtisan;
  error: any;
  subRoute: Subscription = new Subscription();

  constructor(private route: ActivatedRoute, private utils: UtilsService) {
  }

  ngOnInit() {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.taxes = data.parametresSupplier.data.taxeAll;
      this.conditionsReglements = data.parametresSupplier.data.conditionReglementAll;
      this.artisan = data.parametresSupplier.data.artisanByEmail;
    });
  }

  ngOnDestroy(): void {
    this.utils.unsubscribe(this.subRoute);
  }

}

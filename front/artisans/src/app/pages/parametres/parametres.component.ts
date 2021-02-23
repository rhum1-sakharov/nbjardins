import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {KEY_USER, LocalstorageService, MArtisan, MConditionReglement, MTaxe, Utilisateur, UtilsService} from 'rhum1-sakharov-core-lib';


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

  user!: Utilisateur;

  constructor(private route: ActivatedRoute, private utils: UtilsService, private ls: LocalstorageService) {
  }

  ngOnInit() {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.user = this.ls.getItem(KEY_USER);
      this.taxes = data.parametresSupplier.data.taxeAll;
      this.conditionsReglements = data.parametresSupplier.data.conditionReglementAll;
      this.artisan = data.parametresSupplier.data.artisanByEmail;
    });
  }

  ngOnDestroy(): void {
    this.utils.unsubscribe(this.subRoute);
  }

}

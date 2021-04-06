import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {ObservableUtils, ResponsiveUtils} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-a-traiter',
  templateUrl: './a-traiter.component.html',
  styleUrls: ['./a-traiter.component.scss']
})
export class ATraiterComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;

  responsiveUtils = ResponsiveUtils;

  numeroDevisWidth=150;
  clientWidth=350;
  depuisWidth=100;

  devisList !: any[];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisList = data.devisATraiterSupplier.data.devisFindByEmailArtisanAndStatut);
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

}

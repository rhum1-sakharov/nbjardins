import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ObservableUtils} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-refuses',
  templateUrl: './refuses.component.html',
  styleUrls: ['./refuses.component.scss']
})
export class RefusesComponent implements OnInit , OnDestroy {

  subRoute !: Subscription;

  devisList !: any[];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisList = data.devisRefusesSupplier.data.devisFindByEmailArtisanAndStatut);
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

}

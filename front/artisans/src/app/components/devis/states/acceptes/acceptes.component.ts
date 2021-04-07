import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ObservableUtils} from 'rhum1-sakharov-core-lib';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-acceptes',
  templateUrl: './acceptes.component.html',
  styleUrls: ['./acceptes.component.scss']
})
export class AcceptesComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;

  devisList !: any[];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisList = data.devisAcceptesSupplier.data.devisFindByEmailArtisanAndStatut);
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

}

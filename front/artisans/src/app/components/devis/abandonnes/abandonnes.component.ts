import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ObservableUtils} from '../../../../../../core-lib/dist/core-lib';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-abandonnes',
  templateUrl: './abandonnes.component.html',
  styleUrls: ['./abandonnes.component.scss']
})
export class AbandonnesComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;

  devisList !: any[];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisList = data.devisAbandonnesSupplier.data.devisFindByEmailArtisanAndStatut);
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {DateUtils, ObservableUtils, ResponsiveUtils} from 'rhum1-sakharov-core-lib';

@Component({
  selector: 'app-a-traiter',
  templateUrl: './a-traiter.component.html',
  styleUrls: ['./a-traiter.component.scss']
})
export class ATraiterComponent implements OnInit, OnDestroy {

  subRoute !: Subscription;

  responsiveUtils = ResponsiveUtils;
  dateUtils = DateUtils;

  numeroDevisWidth = 170;
  clientWidth = 350;
  depuisWidth = 120;

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

  aTraiterSince(isoDate:string){

    const dateATraiter= DateUtils.getDateFromIso(isoDate);
    const now = new Date();
    const nbDays = DateUtils.getNbDays(now,dateATraiter);

    let display='';
    switch(nbDays){
      case 0:
        display=`aujourd'hui`;
        break;
      case 1:
        display=`1 jour.`;
        break;
      default:
        display=`${nbDays} jours`;
        break;
    }

    return display;

  }

}

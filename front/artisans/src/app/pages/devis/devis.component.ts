import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {Subscription} from 'rxjs/internal/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {ObservableUtils} from 'rhum1-sakharov-core-lib';


@Component({
  selector: 'app-devis',
  templateUrl: './devis.component.html',
  styleUrls: ['./devis.component.scss']
})
export class DevisComponent implements OnInit, OnDestroy, AfterViewInit {

  subRoute !: Subscription;

  items: MenuItem[] = [
    {label: 'à traiter', icon: 'pi pi-fw pi-home', routerLink: ['a-traiter']},
    {label: 'traités', icon: 'pi pi-fw pi-calendar', routerLink: ['traites']},
    {label: 'acceptés', icon: 'pi pi-fw pi-pencil', routerLink: ['acceptes']},
    {label: 'refusés', icon: 'pi pi-fw pi-file', routerLink: ['refuses']},
    {label: 'abandonnés', icon: 'pi pi-fw pi-cog', routerLink: ['abandonnes']}
  ];

  activeItem !: MenuItem;

  devisSupplier: any;


  constructor(public route: ActivatedRoute,
              public router: Router,
              private cd: ChangeDetectorRef,) {

  }

  ngOnInit() {

    this.activeItem = this.getActiveItem(this.router, this.items);

    this.routeSubscription();
  }

  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => this.devisSupplier = data.devisSupplier.data);
  }

  getActiveItem(router: Router, items: MenuItem[]): MenuItem {

    for (const item of items) {
      if (router.url.endsWith(item.routerLink[0])) {
        return item;
      }
    }

    return {};

  }


  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

  ngAfterViewInit(): void {

  }

  getNbDevis(index: number) {

    if(this.devisSupplier){
      switch (index) {
        case 0:
          return this.devisSupplier.nbDevisATraiter.nbResult;
        case 1:
          return this.devisSupplier.nbDevisTraites.nbResult;
        case 2:
          return this.devisSupplier.nbDevisAcceptes.nbResult;
        case 3:
          return this.devisSupplier.nbDevisRefuses.nbResult;
        case 4:
          return this.devisSupplier.nbDevisAbandonnes.nbResult;
        default:
          return 0;
      }
    }

   return 0;

  }
}

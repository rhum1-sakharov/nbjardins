import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {Subscription} from 'rxjs/internal/Subscription';
import {ActivatedRoute, Router} from '@angular/router';
import {ObservableUtils} from 'rhum1-sakharov-core-lib';
import {DevisAnnouncesService} from '../../services/announces/devis-announces.service';


@Component({
  selector: 'app-devis',
  templateUrl: './devis.component.html',
  styleUrls: ['./devis.component.scss']
})
export class DevisComponent implements OnInit, OnDestroy, AfterViewInit {

  subRoute !: Subscription;
  subDevisCreated !: Subscription;
  subDevisRemoved !:Subscription;

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
              private devisAnnounceSvc: DevisAnnouncesService,
              private cd: ChangeDetectorRef,) {

  }

  ngOnInit() {

    this.activeItem = this.getActiveItem(this.router, this.items);

    this.routeSubscription();

    this.devisCreatedSubscription();

    this.devisRemovedSubscription();

  }

  devisCreatedSubscription() {

    this.subDevisCreated = this.devisAnnounceSvc.devisCreated$
      .subscribe(response => this.devisSupplier.nbDevisATraiter.nbResult = this.devisSupplier.nbDevisATraiter.nbResult + 1);

  }

  devisRemovedSubscription(){
    this.subDevisRemoved = this.devisAnnounceSvc.devisRemoved$
      .subscribe(response => this.devisSupplier.nbDevisATraiter.nbResult = this.devisSupplier.nbDevisATraiter.nbResult - 1);
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

    this.router.navigate(['devis', items[0].routerLink[0]]);

    return items[0];

  }


  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
    ObservableUtils.unsubscribe(this.subDevisCreated);
    ObservableUtils.unsubscribe(this.subDevisRemoved);
  }

  ngAfterViewInit(): void {

  }

  getNbDevis(index: number) {

    if (this.devisSupplier) {
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

  getSeverityClass(index: number) {


    switch (index) {
      case 0:
        return '';
      case 1:
        return 'info';
      case 2:
        return 'success';
      case 3:
        return 'danger';
      case 4:
        return 'warning';
      default:
        return '';
    }

  }
}

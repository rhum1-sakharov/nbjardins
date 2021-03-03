///<reference path="../../../../../core-lib/dist/core-lib/lib/models/m-artisan.d.ts"/>
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {
  MArtisan,
  MArtisanBanque,
  MConditionReglement,
  MSG_KEY,
  MSG_SEVERITY,
  MTaxe,
  ToasterService,
  Utilisateur,
  UtilsService
} from 'rhum1-sakharov-core-lib';
import {ParametresHttpService} from '../../http/parametres-http.service';


@Component({
  selector: 'app-parametres',
  templateUrl: './parametres.component.html',
  styleUrls: ['./parametres.component.scss']
})
export class ParametresComponent implements OnInit, OnDestroy {

  taxes: MTaxe[] = [];
  conditionsReglements: MConditionReglement[] = [];
  artisan !: MArtisan;
  artisanBanque !: MArtisanBanque;
  error: any;
  subRoute: Subscription = new Subscription();
  user!: Utilisateur;
  previews = [
    {label: 'devis'},
    {label: 'facture'},
  ];
  selectedPreview = 'devis';


  constructor(public route: ActivatedRoute, private utils: UtilsService,
              private toastSvc: ToasterService,
              private parametresHttp: ParametresHttpService) {
  }

  ngOnInit() {
    this.routeSubscription();
  }


  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.taxes = data.parametresSupplier.data.taxeAll;
      this.conditionsReglements = data.parametresSupplier.data.conditionReglementAll;
      this.artisan = data.parametresSupplier.data.artisanByEmail as MArtisan;
      this.artisanBanque = data.parametresSupplier.data.artisanBanqueByEmailAndPrefere as MArtisanBanque;

      this.artisan.taxe = this.utils.preselectSingleElement(this.taxes, this.artisan.taxe);
      this.artisan.conditionDeReglement = this.utils.preselectSingleElement(this.conditionsReglements, this.artisan.conditionDeReglement);


    });
  }

  ngOnDestroy(): void {
    this.utils.unsubscribe(this.subRoute);
  }

  save() {


    // TODO to implement
    this.artisan.signature = '';

    this.parametresHttp.save(this.artisan).subscribe((response: any) => {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, 'Parmètres enregistrés avec succès.');
    });
  }


}

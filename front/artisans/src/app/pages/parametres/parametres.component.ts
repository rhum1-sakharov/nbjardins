///<reference path="../../../../../core-lib/dist/core-lib/lib/models/m-artisan.d.ts"/>
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {MArtisan, MConditionReglement, MTaxe, Utilisateur, UtilsService} from 'rhum1-sakharov-core-lib';


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
  selectedTaxe !: MTaxe;
  selectedConditionReglement !: MConditionReglement;
  selectedProvision: number = 0;
  selectedSiret: string = '';
  selectedSociete: string = '';
  selectedVille: string = '';
  selectedCodePostal: string='';
  selectedTelephone: string='';
  selectedAdresse: string='';
  selectedFonction: string='';

  constructor(private route: ActivatedRoute, private utils: UtilsService) {
  }

  ngOnInit() {


    this.routeSubscription();
  }


  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.taxes = data.parametresSupplier.data.taxeAll;
      this.conditionsReglements = data.parametresSupplier.data.conditionReglementAll;
      this.artisan = data.parametresSupplier.data.artisanByEmail as MArtisan;

      this.selectedTaxe = this.utils.preselectSingleElement(this.taxes, this.artisan.taxe);
      this.selectedConditionReglement = this.utils.preselectSingleElement(this.conditionsReglements, this.artisan.conditionDeReglement);
      this.selectedProvision = this.artisan.provision;
      this.selectedSiret = this.artisan.siret;
      this.selectedSociete = this.artisan.personne.societe;
      this.selectedCodePostal = this.artisan.personne.codePostal;
      this.selectedAdresse = this.artisan.personne.adresse;
      this.selectedTelephone = this.artisan.personne.numeroTelephone;
      this.selectedVille = this.artisan.personne.ville;
      this.selectedFonction = this.artisan.personne.fonction;

    });
  }

  ngOnDestroy(): void {
    this.utils.unsubscribe(this.subRoute);
  }

}

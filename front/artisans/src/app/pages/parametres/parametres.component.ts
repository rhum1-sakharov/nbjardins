///<reference path="../../../../../core-lib/dist/core-lib/lib/models/m-artisan.d.ts"/>
import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {
  MArtisan,
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
  error: any;
  subRoute: Subscription = new Subscription();
  user!: Utilisateur;
  selectedTaxe !: MTaxe;
  selectedConditionReglement !: MConditionReglement;
  selectedProvision: number = 0;
  selectedSiret: string = '';
  selectedSociete: string = '';
  selectedVille: string = '';
  selectedCodePostal: string = '';
  selectedTelephone: string = '';
  selectedAdresse: string = '';
  selectedFonction: string = '';

  constructor(private route: ActivatedRoute, private utils: UtilsService,
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

  save() {

    this.artisan.taxe = this.selectedTaxe;
    this.artisan.provision = this.selectedProvision;
    this.artisan.conditionDeReglement = this.selectedConditionReglement;

    this.artisan.siret = this.selectedSiret;
    this.artisan.personne.societe = this.selectedSociete;
    this.artisan.personne.fonction = this.selectedFonction;
    this.artisan.personne.adresse = this.selectedAdresse;
    this.artisan.personne.ville = this.selectedVille;
    this.artisan.personne.codePostal = this.selectedCodePostal;
    this.artisan.personne.numeroTelephone = this.selectedTelephone;

    // TODO to implement
    this.artisan.signature = '';

    this.parametresHttp.save(this.artisan).subscribe((response: any) => {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, 'Parmètres enregistrés avec succès.');
    });
  }

}

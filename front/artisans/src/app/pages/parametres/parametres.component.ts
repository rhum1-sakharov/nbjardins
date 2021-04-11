import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {
  CanSave,
  CollectionUtils,
  MArtisan,
  MArtisanBanque,
  MArtisanOption,
  MConditionReglement,
  MODELE_OPTION,
  MSG_KEY,
  MSG_SEVERITY,
  MTaxe,
  ObservableUtils,
  ToasterService,
  Utilisateur
} from 'rhum1-sakharov-core-lib';
import {ParametresHttpService} from '../../services/http/parametres-http.service';


@Component({
  selector: 'app-parametres',
  templateUrl: './parametres.component.html',
  styleUrls: ['./parametres.component.scss']
})
export class ParametresComponent implements OnInit, OnDestroy, CanSave, AfterViewInit {

  taxes: MTaxe[] = [];
  conditionsReglements: MConditionReglement[] = [];
  artisan !: MArtisan;
  artisanBanque !: MArtisanBanque;
  artisanBanqueList !: MArtisanBanque[];
  artisanOptionList: MArtisanOption[] = [];
  error: any;
  subRoute: Subscription = new Subscription();
  user!: Utilisateur;
  previews = [
    {label: 'devis'},
    {label: 'facture'},
  ];
  selectedPreview = 'devis';


  coordonneesBanquaires = MODELE_OPTION.COORDONNEES_BANQUAIRES;
  colonneQuantite = MODELE_OPTION.COLONNE_QUANTITE;
  tvaSaisissableParLigne = MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE;
  provision = MODELE_OPTION.PROVISION;

  source!: any[];

  constructor(public route: ActivatedRoute,
              private toastSvc: ToasterService,
              private cd: ChangeDetectorRef,
              private parametresHttp: ParametresHttpService) {
  }


  ngOnInit() {
    this.routeSubscription();
  }


  routeSubscription() {
    this.subRoute = this.route.data.subscribe((data) => {

      this.taxes = data.parametresSupplier.data.taxeAll;
      this.conditionsReglements = data.parametresSupplier.data.conditionReglementAll;
      this.artisanOptionList = data.parametresSupplier.data.artisanOptionFindByEmail;
      this.artisan = data.parametresSupplier.data.artisanFindByEmail as MArtisan;
      this.artisanBanqueList = data.parametresSupplier.data.artisanBanqueFindByEmail;
      this.artisanBanque = this.getArtisanBanquePrefere(this.artisanBanqueList) as MArtisanBanque;


      this.artisan.taxe = CollectionUtils.preselectSingleElement(this.taxes, this.artisan.taxe);
      this.artisan.conditionDeReglement = CollectionUtils.preselectSingleElement(this.conditionsReglements, this.artisan.conditionDeReglement);


    });
  }

  ngOnDestroy(): void {
    ObservableUtils.unsubscribe(this.subRoute);
  }

  save() {

    // TODO to implement
    this.artisan.signature = '';

    this.parametresHttp.save(this.artisan, this.artisanOptionList, this.artisanBanqueList).subscribe((response: any) => {

      if (!CollectionUtils.isNoe(response.data.saveArtisanBanqueList)) {
        this.artisanBanqueList = [...response.data.saveArtisanBanqueList];
        this.artisanBanque = this.getArtisanBanquePrefere(this.artisanBanqueList) as MArtisanBanque;
      }
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.SUCCESS, 'Paramètres enregistrés avec succès.');
    });

  }


  getModeleOptionLabel(modeleOption: MODELE_OPTION) {

    switch (modeleOption) {
      case MODELE_OPTION.COLONNE_QUANTITE:
        return 'Afficher les colonnes quantité et prix unitaire HT';
      case MODELE_OPTION.COORDONNEES_BANQUAIRES:
        return 'Afficher les coordonnées banquaires';
      case MODELE_OPTION.TVA_SAISISSABLE_PAR_LIGNE:
        return 'TVA saisissable par ligne';
      case MODELE_OPTION.PROVISION:
        return 'Afficher la provision';
      default :
        return 'Modele Option inconnu';
    }
  }


  getArtisanBanquePrefere(abList: MArtisanBanque[]) {
    if (abList) {
      for (const ab of abList) {
        if (ab.prefere === true) {
          return ab;
        }
      }
    }
    return null;
  }

  getArtisanOption(mo: MODELE_OPTION) {

    if (this.artisanOptionList) {
      for (const ao of this.artisanOptionList) {
        if (ao.modeleOption === mo) {
          return ao.actif;
        }
      }
    }

    return null;
  }


  setArtisanOption(mo: MODELE_OPTION, $event: any) {
    if (this.artisanOptionList) {
      for (const ao of this.artisanOptionList) {
        if (ao.modeleOption === mo) {
          ao.actif = $event;
        }
      }
    }
  }

  updateArtisanBanqueList($event: MArtisanBanque[]) {

    this.artisanBanqueList = $event;
    if (!CollectionUtils.isNoe(this.artisanBanqueList)) {
      for (const ab of this.artisanBanqueList) {
        if (ab.prefere) {
          this.artisanBanque = ab;
          break;
        }
      }
    }
  }

  isDirty(): boolean {
    return true;
  }

  ngAfterViewInit(): void {

  }
}

import {Injectable} from '@angular/core';
import {HttpService} from "../techniques/http.service";
import {CONTEXT_PATH} from "../../constants";
import {MDemandeDeDevis} from "../../models/m-demande-de-devis";
import {HttpParams} from "@angular/common/http";
import {TOKEN_APP} from "../../../constants";

export const URL_POST_DEVIS_DEMANDER_DEVIS = `${CONTEXT_PATH}/devis/demander-devis`;

@Injectable({
  providedIn: 'root'
})
export class DemandeDeDevisService {

  constructor(private httpSvc: HttpService) {
  }

  send(demandeDeDevis: MDemandeDeDevis) {

    const params = new HttpParams().set('app-token', TOKEN_APP);

    return this.httpSvc.post(URL_POST_DEVIS_DEMANDER_DEVIS, demandeDeDevis, params);
  }
}

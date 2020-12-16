import {Injectable} from '@angular/core';
import {HttpService} from "../techniques/http.service";
import {MDemandeDeDevis} from "../../models/m-demande-de-devis";
import {CONTEXT_PATH} from "../../constants";
import {MRequest} from "../../models/m-request";

export const URL_POST_DEVIS_DEMANDER_DEVIS = `${CONTEXT_PATH}/devis/demander-devis`;

@Injectable({
  providedIn: 'root'
})
export class DemandeDeDevisService {

  constructor(private httpSvc: HttpService) {
  }

  send(request: MRequest<MDemandeDeDevis>) {
    return this.httpSvc.post(URL_POST_DEVIS_DEMANDER_DEVIS, request);
  }
}

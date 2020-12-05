import {Injectable} from '@angular/core';
import {HttpService} from "../techniques/http.service";
import {MDemandeDeDevis} from "../../models/m-demande-de-devis";
import {CONTEXT_PATH} from "../../constants";

export const URL_POST_DEVIS_DEMANDER_DEVIS = `${CONTEXT_PATH}/devis/demander-devis`;

@Injectable({
  providedIn: 'root'
})
export class DemandeDeDevisService {

  constructor(private httpSvc: HttpService) {
  }

  send(demandeDeDevis: MDemandeDeDevis) {
    return this.httpSvc.post(URL_POST_DEVIS_DEMANDER_DEVIS,demandeDeDevis);
  }
}

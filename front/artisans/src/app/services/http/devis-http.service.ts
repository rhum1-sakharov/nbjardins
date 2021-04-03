import {Injectable} from '@angular/core';
import {HttpService, STATUT_DEVIS, URL_GRAPHQL} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class DevisHttpService {

  constructor(private httpSvc: HttpService) {

  }

  /**
   * Préparer les infos nécessaires à l'écran  devis
   * @returns {Observable<Object>}
   */
  prepare(emailArtisan: string) {

    const query = `{        
       devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.A_TRAITER}){
         nbResult
      }    
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }
}

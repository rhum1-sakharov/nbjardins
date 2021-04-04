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
      ${this.devisCountByEmailArtisanAndStatut(emailArtisan)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  devisCountByEmailArtisanAndStatut(emailArtisan: string){

    const query = `        
       
       nbDevisATraiter: devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.A_TRAITER}){
         nbResult }
         
       nbDevisTraites: devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.TRAITE}){
         nbResult }  
         
       nbDevisAcceptes: devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.ACCEPTE}){
         nbResult }    
         
       nbDevisRefuses: devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.REFUSE}){
         nbResult }    
       
       nbDevisAbandonnes: devisCountByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${STATUT_DEVIS.ABANDON}){
         nbResult }          
       
         `;

    return query;

  }

}

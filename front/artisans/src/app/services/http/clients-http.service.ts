import {Injectable} from '@angular/core';
import {HttpService, URL_GRAPHQL} from '../../../../../core-lib/dist/core-lib';

@Injectable({
  providedIn: 'root'
})
export class ClientsHttpService {

  constructor( private httpSvc: HttpService) { }

  /**
   * Préparer les infos nécessaires à l'écran  devis
   * @returns {Observable<Object>}
   */
  findByEmailArtisan(emailArtisan: string) {

    const query = `{        
               clientFindByEmailArtisan( emailArtisan: "${emailArtisan}"){
                  id
                  personne{
                     id
                     nom
                     prenom
                     societe                  
                  }
               
               }
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }
}

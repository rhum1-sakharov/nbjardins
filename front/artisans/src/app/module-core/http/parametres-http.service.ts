import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ParametresHttpService {

  constructor(private http: HttpClient) {

  }

  /**
   * Préparer les infos nécessaires à l'écran de parametrage de l'artisan
   * @returns {Observable<Object>}
   */
  prepare(email: string) {

    const query = `{
      
      taxeAll{
        id
        nom
        taux
      }
      
       conditionReglementAll{
          id
          condition
       }
       
       artisanByEmail(email: "${email}"){
          id
          provision
          siret
          validiteDevisMois
          taxe {
            id
            nom
            taux
          }
          conditionDeReglement {
            id
            condition
          }
          personne {
            id
            nom
            prenom
            email
          }
       }       
    }`;

    return this.http.post('api/graphql', query);
  }
}

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
  prepare(){

    const query = `{
      
      allTaxes{
        id
        nom
        taux
      }
      
       allConditionsReglements{
          id
          condition
       }
    }`;

    return this.http.post('api/graphql', query);
  }
}

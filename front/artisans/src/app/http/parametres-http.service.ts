import {Injectable} from '@angular/core';
import {HttpService, MArtisan} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class ParametresHttpService {

  constructor(private httpSvc: HttpService) {

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
            societe
            fonction
            ville
            adresse
            codePostal 
            numeroTelephone          
          }
       }       
    }`;

    return this.httpSvc.post('api/graphql', query);
  }

  save(artisan: MArtisan) {

    const query = `      
    mutation saveArtisan{
    saveArtisan(artisan:{
        id: "${artisan.id}"
        personne: {
            numeroTelephone: "${artisan.personne.numeroTelephone}"
        }
    }){
        id
    }
}        
    `;

    return this.httpSvc.post('api/graphql', query);
  }
}

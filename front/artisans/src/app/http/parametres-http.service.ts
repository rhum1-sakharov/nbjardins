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
    
      artisanBanqueByEmailAndPrefere(email: "${email}", prefere: ${true}){
          iban
          rib
      }
      
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
          logo    
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
        id: "${artisan.id}",
        taxe: {
          id: "${artisan.taxe.id}"
          nom: "${artisan.taxe.nom}"
          taux: ${artisan.taxe.taux}
        }
        conditionDeReglement: {
          id: "${artisan.conditionDeReglement.id}"
          condition: "${artisan.conditionDeReglement.condition}"
        }
        logo: "${artisan.logo}"
        provision: ${artisan.provision}
        siret: "${artisan.siret}"
        validiteDevisMois: ${artisan.validiteDevisMois}      
        signature: ""   
        personne: {
            id: "${artisan.personne.id}"
            nom: "${artisan.personne.nom}"
            prenom: "${artisan.personne.prenom}"
            email: "${artisan.personne.email}"            
            numeroTelephone: "${artisan.personne.numeroTelephone}"
            adresse: "${artisan.personne.adresse}"
            codePostal: "${artisan.personne.codePostal}"
            ville: "${artisan.personne.ville}"
            fonction: "${artisan.personne.fonction}"
            societe: "${artisan.personne.societe}"
        }
    }){
        id
    }
}        
    `;

    return this.httpSvc.post('api/graphql', query);
  }
}

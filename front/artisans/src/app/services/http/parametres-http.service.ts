import {Injectable} from '@angular/core';
import {CollectionUtils, HttpService, MArtisan, MArtisanBanque, MArtisanOption} from 'rhum1-sakharov-core-lib';

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
      
       artisanBanqueFindByEmail(email: "${email}"){
          id
          artisan {
            id
          }
          iban
          rib
          banque
          prefere
      }
      
      artisanOptionFindByEmail(email: "${email}"){
         id
         artisan {
           id
         }
         modeleOption
         actif      
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
       
       artisanFindByEmail(email: "${email}"){
          id
          provision
          emailPro
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

  save(artisan: MArtisan, artisanOptionList: MArtisanOption[], artisanBanqueList: MArtisanBanque[]) {

    const query = `      
    mutation saveArtisan{
    
      ${this.generateSaveArtisan(artisan)}
      ${this.generateSaveArtisanOptionList(artisanOptionList)}
      ${this.generateRemoveArtisanBanqueByEmail(artisan.personne.email)}
      ${this.generateSaveArtisanBanqueList(artisanBanqueList)}
   
    }`;

    return this.httpSvc.post('api/graphql', query);
  }

  private generateRemoveArtisanBanqueByEmail(email: string) {

    return ` removeArtisanBanqueByEmail( email: {email: "${email}"}){
       nbDeleted
    }`;
  }

  private generateSaveArtisanBanqueList(artisanBanqueList: MArtisanBanque[]) {

    let str = '';

    if(!CollectionUtils.isNoe(artisanBanqueList)){

      str += ` saveArtisanBanqueList( artisanBanqueList:[`;
      let index=1;
      for (const ab of artisanBanqueList) {
        str+=`{
          id: "${ab.id}"      
          artisan: {
            id: "${ab.artisan.id}"
          } 
          iban: "${ab.iban}"
          rib: "${ab.rib}"
          banque: "${ab.banque}"
          prefere: ${ab.prefere}        
        }
        `;
      }

      str+=`]){
       id
       artisan {
         id
       }
       banque
       iban
       rib
       prefere
      }`;


    }




    return str;

  }

  private generateSaveArtisan(artisan: MArtisan) {
    let str = `

    saveArtisan(artisan:{
      id: "${artisan.id}"
      emailPro: "${artisan.emailPro}"
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
    }`;

    return str;
  }

  private generateSaveArtisanOptionList(artisanOptionList: MArtisanOption[]): string {

    let str = '';

    for (const ao of artisanOptionList) {
      str += `
      
   ${ao.modeleOption} : saveArtisanOption( artisanOption: {
             
          id: "${ao.id}"      
          artisan: {
            id: "${ao.artisan.id}"
          } 
          modeleOption: ${ao.modeleOption}
          actif: ${ao.actif}
      
      }){
            id
      }
      
      `;
    }

    return str;
  }
}

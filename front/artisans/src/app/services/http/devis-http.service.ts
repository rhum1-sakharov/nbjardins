import {Injectable} from '@angular/core';
import {GRAPHQL_TYPE, GraphqlUtils, HttpService, MDevis, STATUT_DEVIS, URL_GRAPHQL} from 'rhum1-sakharov-core-lib';
import {finalize} from 'rxjs/operators';
import {LoadingService} from '../loading/loading.service';

@Injectable({
  providedIn: 'root'
})
export class DevisHttpService {

  constructor(private httpSvc: HttpService, private loadingSvc: LoadingService) {

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

  prepareDevisATraiter(emailArtisan: string) {

    const query = `{        
      ${this.devisFindByEmailArtisanAndStatut(emailArtisan, STATUT_DEVIS.A_TRAITER)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  prepareDevisTraites(emailArtisan: string) {

    const query = `{        
      ${this.devisFindByEmailArtisanAndStatut(emailArtisan, STATUT_DEVIS.TRAITE)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  prepareDevisAbandonnes(emailArtisan: string) {

    const query = `{        
      ${this.devisFindByEmailArtisanAndStatut(emailArtisan, STATUT_DEVIS.ABANDON)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  prepareDevisAcceptes(emailArtisan: string) {

    const query = `{        
      ${this.devisFindByEmailArtisanAndStatut(emailArtisan, STATUT_DEVIS.ACCEPTE)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  prepareDevisRefuses(emailArtisan: string) {

    const query = `{        
      ${this.devisFindByEmailArtisanAndStatut(emailArtisan, STATUT_DEVIS.REFUSE)}         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);
  }

  devisFindByEmailArtisanAndStatut(emailArtisan: string, statutDevis: STATUT_DEVIS) {

    const query = `        
       
        devisFindByEmailArtisanAndStatut(emailArtisan: "${emailArtisan}", statutDevis: ${statutDevis}){
         
         id
         numeroDevis
         dateATraiter       
         dateAbandon
         dateRefuse
         dateTraite
         dateAccepte
         clientNom
         clientPrenom
         clientSociete
         client{
           id
         }
         
       }        
       
         `;

    return query;

  }

  devisCountByEmailArtisanAndStatut(emailArtisan: string) {

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

  createDevisATraiter(emailArtisan: string, emailClient: string | null) {

    const query = `      
    mutation createDevisATraiter{
    
      createDevisATraiter( emailArtisan: {email: "${emailArtisan}"}, emailClient: {email: "${emailClient}"}){
         id
         numeroDevis
         dateATraiter       
         dateAbandon
         dateRefuse
         dateTraite
         dateAccepte
         clientNom
         clientPrenom
         clientSociete
         client{
           id
           personne{
             id
             nom
             prenom
             email
           }
         }
      }
    
         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);

  }

  removeDevis(idDevis: string) {

    const query = `      
    mutation removeDevis{
    
      removeDevis( idDevis: {id: "${idDevis}"}){
         id         
      }   
         
    }`;

    return this.httpSvc.post(URL_GRAPHQL, query);

  }

  saveDevis(devis: MDevis) {
    this.loadingSvc.announceLoading(true);

    const query = `  
    mutation saveDevis{    
      saveDevis( devis: {
      
       ${GraphqlUtils.setInput(devis,'id',GRAPHQL_TYPE.STRING)}              
       ${GraphqlUtils.setInput(devis,'statut',GRAPHQL_TYPE.ENUM)}
       ${GraphqlUtils.setInput(devis,'numeroDevis',GRAPHQL_TYPE.STRING)}
       ${GraphqlUtils.setInput(devis,'totalHT',GRAPHQL_TYPE.NUMBER)}    
       ${GraphqlUtils.setInput(devis,'lieu',GRAPHQL_TYPE.STRING)}
       ${GraphqlUtils.setInput(devis,'sujet',GRAPHQL_TYPE.STRING)}
       
       ${GraphqlUtils.setInput(devis,'dateATraiter',GRAPHQL_TYPE.DATE)}
       ${GraphqlUtils.setInput(devis,'dateAbandon',GRAPHQL_TYPE.DATE)}
       ${GraphqlUtils.setInput(devis,'dateRefuse',GRAPHQL_TYPE.DATE)}
       ${GraphqlUtils.setInput(devis,'dateTraite',GRAPHQL_TYPE.DATE)}
       ${GraphqlUtils.setInput(devis,'dateAccepte',GRAPHQL_TYPE.DATE)}
       ${GraphqlUtils.setInput(devis,'dateDevis',GRAPHQL_TYPE.DATE)}       
      
       
       artisan: {         
         ${GraphqlUtils.setInput(devis.artisan,'id',GRAPHQL_TYPE.STRING)}       
        
       }
        
        ${GraphqlUtils.setInput(devis,'artisanLogo',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanSiret',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanSociete',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanFonction',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanAdresse',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanVille',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanCodePostal',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanTelephone',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanEmail',GRAPHQL_TYPE.STRING)}       
        ${GraphqlUtils.setInput(devis,'artisanSignature',GRAPHQL_TYPE.STRING)}               
       
           
       
       ${GraphqlUtils.setInput(devis,'clientNom',GRAPHQL_TYPE.STRING)}       
       ${GraphqlUtils.setInput(devis,'clientPrenom',GRAPHQL_TYPE.STRING)}       
       ${GraphqlUtils.setInput(devis,'clientAdresse',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientVille',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientCodePostal',GRAPHQL_TYPE.STRING)}       
       ${GraphqlUtils.setInput(devis,'clientTelephone',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientEmail',GRAPHQL_TYPE.STRING)}   
       ${GraphqlUtils.setInput(devis,'clientSignature',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientSiret',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientSociete',GRAPHQL_TYPE.STRING)}      
       ${GraphqlUtils.setInput(devis,'clientFonction',GRAPHQL_TYPE.STRING)}       
       
       
      }){    
        id
        numeroDevis  
        lieu
        sujet
         dateATraiter
         dateTraite
         dateAccepte
         dateRefuse
         dateAbandon
         dateDevis

        
        artisan{
          id
          personne{
            nom
            prenom
            societe
          }
        }
        
        artisanLogo 
        artisanSiret 
        artisanSociete 
        artisanFonction 
        artisanAdresse 
        artisanVille 
        artisanCodePostal 
        artisanTelephone 
        artisanEmail 
        artisanSignature    
        
        client{
          id
        } 
       clientNom 
       clientPrenom 
       clientAdresse 
       clientVille 
       clientCodePostal 
       clientTelephone 
       clientEmail 
       clientSignature 
       clientSiret 
       clientSociete 
       clientFonction 
      }
    }
    `;

    return this.httpSvc.post(URL_GRAPHQL, query).pipe(
      finalize(() => this.loadingSvc.announceLoading(false))
    );
  }

  findDevis(idDevis: string) {

    this.loadingSvc.announceLoading(true);

    const query = `  {    
      devisFindById( idDevis: "${idDevis}"){    
        id
        numeroDevis  
        statut
        totalHT
        lieu
        sujet
         dateATraiter
         dateTraite
         dateAccepte
         dateRefuse
         dateAbandon
         dateDevis

        
        artisan{
          id
          personne{
            nom
            prenom
            societe
          }
        }
        
        artisanLogo 
        artisanSiret 
        artisanSociete 
        artisanFonction 
        artisanAdresse 
        artisanVille 
        artisanCodePostal 
        artisanTelephone 
        artisanEmail 
        artisanSignature    
        
        client{
          id
        } 
       clientNom 
       clientPrenom 
       clientAdresse 
       clientVille 
       clientCodePostal 
       clientTelephone 
       clientEmail 
       clientSignature 
       clientSiret 
       clientSociete 
       clientFonction 
      }
    }
    `;

    return this.httpSvc.post(URL_GRAPHQL, query).pipe(
      finalize(() => this.loadingSvc.announceLoading(false))
    );

  }
}

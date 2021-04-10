import {Injectable} from '@angular/core';
import {HttpService, STATUT_DEVIS, URL_GRAPHQL} from 'rhum1-sakharov-core-lib';
import {finalize} from 'rxjs/operators';
import {LoadingService} from '../loading/loading.service';

@Injectable({
  providedIn: 'root'
})
export class DevisHttpService {

  constructor(private httpSvc: HttpService, private loadingSvc:LoadingService) {

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

  findDevis(idDevis: string) {

    this.loadingSvc.announceLoading(true);

    const query = `  {    
      devisFindById( idDevis: "${idDevis}"){    
        id
        numeroDevis  
        lieu
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
      finalize(()=>this.loadingSvc.announceLoading(false))
    );

  }
}

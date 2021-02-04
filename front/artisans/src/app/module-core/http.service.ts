import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {catchError, delay} from 'rxjs/operators';

import {Observable, of} from 'rxjs';
import {Router} from '@angular/router';
import {MSG_KEY, MSG_SEVERITY, ToasterService} from 'rhum1-sakharov-core-lib';
import {AuthService, Utilisateur} from '../login/auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  private manageErrors$ = (changes: Observable<any>) => changes.pipe(
    delay(5),
    catchError(err => this.handleError(err))
  )

  constructor(private http: HttpClient, private toastSvc: ToasterService,  private router: Router, private authSvc: AuthService) {
  }

  get(url: string, params?: HttpParams, loading = true): any {
    return this.http.get(url, {params}).pipe(this.manageErrors$);
  }

  post(url: string, body: any, params?: HttpParams, loading = true): any {
    return this.http.post(url, body, {params}).pipe(this.manageErrors$);
  }

  handleError(err: HttpErrorResponse): Observable<any> {

    if (err.error) {

      if (err.error.errorMessages) {
        this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, 'Traitement en erreur.',
          err.error.errorMessages.map((item: string) => item).join(' '));
      } else {
        this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, 'Erreur interne.', err.message);
      }

      // si non autorisé, on redirige vers la fenetre d'authentification
      if (err.status === 401) {
        this.authSvc.connectedUser = new Utilisateur();
        setTimeout(() => {
          this.redirectTo(localStorage.getItem(this.authSvc.keyRedirectAuthorizedUrl));
        }, 5000);

      }
    }
    return of(null);
  }

  /**
   * https://stackoverflow.com/questions/40983055/how-to-reload-the-current-route-with-the-angular-2-router
   * @param {string | null} uri
   */
  redirectTo(uri: string | null): void{
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() =>
      this.router.navigate([uri]));
  }

  handleServerError(response: any): Observable<any> {

    if (response.error) {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, response.errorMessages.map((item: string) => item).join(' '));
    }
    return of(response);

  }

}

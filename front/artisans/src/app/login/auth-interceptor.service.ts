import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private authSvc: AuthService) {
  }

  /**
   * Récupérer le token du localstorage
   * S'il existe, on ajoute le header d'authorization avec le Bearer {token
   * @param {HttpRequest<any>} req
   * @param {HttpHandler} next
   * @returns {Observable<HttpEvent<any>>}
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem(this.authSvc.keyJwtToken);

    if (token) {
      req = req.clone({setHeaders: {Authorization: 'Bearer ' + token}});
    }

    return next.handle(req);
  }
}

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {KEY_JWT_TOKEN} from '../constants/constants';
import {LocalstorageService} from './localstorage.service';


@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private ls: LocalstorageService) {
  }

  /**
   * Récupérer le token du localstorage
   * S'il existe, on ajoute le header d'authorization avec le Bearer {token
   * @param {HttpRequest<any>} req
   * @param {HttpHandler} next
   * @returns {Observable<HttpEvent<any>>}
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = this.ls.getItem(KEY_JWT_TOKEN);

    if (token) {
      req = req.clone({setHeaders: {Authorization: 'Bearer ' + token}});
    }

    return next.handle(req);
  }
}

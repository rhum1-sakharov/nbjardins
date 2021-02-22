import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService, UserRight} from './auth.service';
import {KEY_REDIRECT_AUTHORIZED_URL, KEY_USER} from '../constants/constants';
import {LocalstorageService} from './localstorage.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authSvc: AuthService, private router: Router, private ls: LocalstorageService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    this.storeRouteToActivate(route.data, state);

    const userRight = route.data as UserRight;
    const user = this.ls.getItem(KEY_USER);

    if (this.authSvc.hasRight(user, userRight)) {
      return true;
    } else {
      window.location.href = this.authSvc.URL_INITIATE_GOOGLE_OAUTH_ARTISAN;
    }
    return false;
  }

  private storeRouteToActivate(routeData: any, state: RouterStateSnapshot): void {
    let url: string = state.url;

    // suppression de la route secondaire
    const indexOfParenthesis = url.indexOf('(');
    if (indexOfParenthesis >= 0) {
      url = url.substr(0, indexOfParenthesis);
    }

    // stockage dans le localstorage
    this.ls.setItem(KEY_REDIRECT_AUTHORIZED_URL, url);

  }
}

import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService, UserRight} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authSvc: AuthService, private router: Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    this.storeRouteToActivate(route.data, state);

    const userRight = route.data as UserRight;

    if (this.authSvc.hasRight(this.authSvc.connectedUser, userRight)) {
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
    localStorage.setItem(this.authSvc.keyRedirectUrl, url);
  }
}

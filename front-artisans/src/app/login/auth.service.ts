import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly keyJwtToken = 'jwt_token';
  readonly keyRedirectUrl = 'redirect_url';
  readonly URL_INITIATE_GOOGLE_OAUTH_ARTISAN = `api/authorization/initiate-google-oauth?typePersonne=ARTISAN`;

  constructor() {
  }

  /**
   * Stocker le token dans le localStorage s'il est present en tant que parametre dans l'url
   */
  storeTokenFromCurrentUrl(): void {

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const token: string = urlParams.get('token') || '';

    if (token !== '') {
      localStorage.removeItem(this.keyJwtToken);
      localStorage.setItem(this.keyJwtToken, token);

      const helper = new JwtHelperService();
      const decodedToken = helper.decodeToken(token);
      const expirationDate = helper.getTokenExpirationDate(token);
      const isExpired = helper.isTokenExpired(token);

      console.log(decodedToken, expirationDate, isExpired);

    }
  }

  hasRight(user: any, userRight: UserRight): boolean{

    return false;
  }
}


export interface UserRight {
  role: ROLE;
}

export enum ROLE {
  ROLE_ARTISAN = 'ROLE_ARTISAN',
  ROLE_CLIENT = 'ROLE_CLIENT'
}

export const USER_RIGHT_ARTISAN: UserRight = {role: ROLE.ROLE_ARTISAN};
export const USER_RIGHT_CLIENT: UserRight = {role: ROLE.ROLE_CLIENT};

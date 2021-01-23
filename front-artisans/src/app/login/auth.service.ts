import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly keyJwtToken = 'jwt_token';
  readonly keyRedirectAuthorizedUrl = 'redirect_authorized_url';
  readonly URL_INITIATE_GOOGLE_OAUTH_ARTISAN = `api/authorization/initiate-google-oauth?typePersonne=ARTISAN`;
  connectedUser: Utilisateur = new Utilisateur();

  constructor(private router: Router) {
  }

  /**
   * Pour démarrer une session utilisateur :
   *
   * Si le token est present dans l'url
   *    creer l'utilisateur connecté à partir du token
   * Sinon,
   *    si le token est present dans le localstorage
   *      creer l'utilisateur connecté à partir du token
   *    sinon
   *      creer l'utilisateur connecté avec des valeurs à vide
   *
   * Si une url de redirection existe dans le localStorage et que l'utilisateur est reconnu
   *    on redirige sur cette url
   *
   */
  startUserSession(): void {

    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const token: string = urlParams.get('token') || '';

    if (token !== '') {
      localStorage.setItem(this.keyJwtToken, token);
      this.createUser(token);
    } else {
      const localToken = localStorage.getItem(this.keyJwtToken);
      if (localToken !== null) {
        this.createUser(localToken);
      } else {
        this.connectedUser = new Utilisateur();
      }
    }

    const redirectUrl = localStorage.getItem(this.keyRedirectAuthorizedUrl);
    if (redirectUrl && this.connectedUser.email !== '') {
      this.router.navigate([redirectUrl]);
    }
  }

  /**
   * Verifier que l'utilisateur a le role requis
   * @param {Utilisateur} user
   * @param {UserRight} userRight
   * @returns {boolean} retourne vrai si le role est trouvé
   */
  hasRight(user: Utilisateur, userRight: UserRight): boolean {

    if (user && user.roles.length > 0) {
      const roleArr = user.roles.filter(item => item === userRight.role);
      if (roleArr) {
        return true;
      }
    }
    return false;
  }

  private createUser(token: string): void {

    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);
    const expirationDate = helper.getTokenExpirationDate(token);
    const isExpired = helper.isTokenExpired(token);
    console.log(decodedToken, expirationDate, isExpired);

    this.connectedUser = new Utilisateur();
    this.connectedUser.email = decodedToken.sub;
    this.connectedUser.nom = decodedToken.nom;
    this.connectedUser.prenom = decodedToken.prenom;
    this.connectedUser.roles = this.getRoles(decodedToken.roles);

  }

  /**
   * Transformer les roles de type string[] en roles de type ROLE[]
   * @param {string[]} rolesArr
   * @returns {ROLE[]}
   */
  private getRoles(rolesArr: string[]): ROLE[] {

    const roles: ROLE[] = [];
    for (const role of rolesArr) {
      switch (role) {
        case ROLE.ROLE_ARTISAN:
          roles.push(ROLE.ROLE_ARTISAN);
          break;
        case ROLE.ROLE_CLIENT:
          roles.push(ROLE.ROLE_CLIENT);
          break;
      }
    }

    return roles;
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

export class Utilisateur {

  nom = '';
  prenom = '';
  email = '';
  roles: ROLE[] = [];
}

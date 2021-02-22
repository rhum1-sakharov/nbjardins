import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Router} from '@angular/router';
import {KEY_JWT_TOKEN, KEY_REDIRECT_AUTHORIZED_URL, KEY_USER} from '../constants/constants';
import {ConfirmationService} from 'primeng/api';
import {LocalstorageService} from './localstorage.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly URL_INITIATE_GOOGLE_OAUTH_ARTISAN = `api/authorization/initiate-google-oauth?typePersonne=ARTISAN`;

  constructor(private router: Router, private confirmationSvc: ConfirmationService, private ls:LocalstorageService) {
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
      this.ls.setItem(KEY_JWT_TOKEN, token);
      this.createUser(token);
    } else {
      const localToken = this.ls.getItem(KEY_JWT_TOKEN);
      if (localToken !== null) {
        this.createUser(localToken);
      } else {
        localStorage.removeItem(KEY_USER);
      }
    }

    const redirectUrl = this.ls.getItem(KEY_REDIRECT_AUTHORIZED_URL);

    const user: Utilisateur = this.ls.getItem(KEY_USER);
    if (redirectUrl && user && user.email !== '') {
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

  confirmReconnect() {

    // on reset l'utilisateur
    localStorage.removeItem(KEY_USER);

    this.confirmationSvc.confirm({
      message: 'Votre session est invalide, veuillez vous reconnecter.',
      header: 'Confirmation',
      icon: 'pi pi-exclamation-triangle',

      accept: () => {
        window.location.href = this.URL_INITIATE_GOOGLE_OAUTH_ARTISAN;
      },
      reject: () => {
        this.router.navigate(['/']);
      }
    })
  }

  private createUser(token: string): void {

    const helper = new JwtHelperService();
    const decodedToken = helper.decodeToken(token);
    const expirationDate = helper.getTokenExpirationDate(token);
    const isExpired = helper.isTokenExpired(token);
    console.log(decodedToken, expirationDate, isExpired);

    const user = new Utilisateur();
    user.email = decodedToken.sub;
    user.nom = decodedToken.nom;
    user.prenom = decodedToken.prenom;
    user.roles = this.getRoles(decodedToken.roles);

    this.ls.setItem(KEY_USER, user);

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

import {Injectable} from '@angular/core';
import {DevisHttpService} from '../../../../services/http/devis-http.service';
import {AuthService, KEY_USER, LocalstorageService} from 'rhum1-sakharov-core-lib';
import {ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TraitesResolverService {

  constructor(private devisHttp: DevisHttpService, private ls: LocalstorageService, private auth: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> |  any {

    const user = this.ls.getItem(KEY_USER);
    return this.devisHttp.prepareDevisTraites(user.email);

  }
}

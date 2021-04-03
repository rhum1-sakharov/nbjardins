import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {AuthService, KEY_USER, LocalstorageService} from 'rhum1-sakharov-core-lib';
import {Observable} from 'rxjs';
import {Promise} from 'q';
import {DevisHttpService} from '../../services/http/devis-http.service';

@Injectable({
  providedIn: 'root'
})
export class DevisResolverService {

  constructor(private devisHttp: DevisHttpService, private ls: LocalstorageService, private auth: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    const user = this.ls.getItem(KEY_USER);
    return this.devisHttp.prepare(user.email);

  }
}

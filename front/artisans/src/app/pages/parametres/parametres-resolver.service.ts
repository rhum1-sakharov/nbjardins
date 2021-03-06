import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {ParametresHttpService} from '../../services/http/parametres-http.service';
import {AuthService, KEY_USER, LocalstorageService} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class ParametresResolverService implements Resolve<any> {

  constructor(private parametresHttp: ParametresHttpService, private ls: LocalstorageService, private auth: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | any {

    const user = this.ls.getItem(KEY_USER);
    return this.parametresHttp.prepare(user.email);

  }
}

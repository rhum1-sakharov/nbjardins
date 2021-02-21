import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Promise} from 'q';
import {ParametresHttpService} from '../../module-core/http/parametres-http.service';
import {AuthService} from '../../login/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ParametresResolverService implements Resolve<any> {

  constructor(private parametresHttp: ParametresHttpService, private auth: AuthService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return this.parametresHttp.prepare(this.auth.connectedUser.email);

  }
}

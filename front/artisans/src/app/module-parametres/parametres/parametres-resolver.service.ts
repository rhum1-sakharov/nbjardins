import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Promise} from 'q';
import {ParametresHttpService} from '../../module-core/http/parametres-http.service';

@Injectable({
  providedIn: 'root'
})
export class ParametresResolverService implements Resolve<any> {

  constructor(private parametresHttp: ParametresHttpService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {

    return this.parametresHttp.prepare();

  }
}

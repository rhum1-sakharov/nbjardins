import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {CanSave} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class SaveGuardService implements CanDeactivate<CanSave> {


  constructor() {
  }

  canDeactivate(component: CanSave, currentRoute: ActivatedRouteSnapshot, currentState: RouterStateSnapshot,
                nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }


}

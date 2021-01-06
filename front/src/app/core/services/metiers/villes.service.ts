import {Injectable} from '@angular/core';
import {HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {HttpService} from 'rhum1-sakharov-core-lib';

export const URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES = `https://geo.api.gouv.fr/communes`;

@Injectable({
  providedIn: 'root'
})
export class VillesService {

  constructor(private httpSvc: HttpService) {
  }

  search(text: string): Observable<any> {

    const params = new HttpParams()
      .set('nom', text)
      .set('fields', 'codesPostaux')
      .set('limit', '10');

    return this.httpSvc.get(URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES, params);
  }
}

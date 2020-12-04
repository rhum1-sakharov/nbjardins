import {Injectable} from '@angular/core';
import {HttpParams} from "@angular/common/http";
import {HttpService} from "../techniques/http.service";

export const URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES = `https://geo.api.gouv.fr/communes`;

@Injectable({
  providedIn: 'root'
})
export class VillesService {

  constructor(private httpSvc: HttpService) {
  }

  search(text: string) {

    const params = new HttpParams()
      .set('nom', text)
      .set('fields', 'codesPostaux')
      .set('limit', '10');

    return this.httpSvc.get(URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES, params);
  }
}

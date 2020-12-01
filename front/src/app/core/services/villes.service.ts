import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {of} from "rxjs/internal/observable/of";

export const URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES = `https://geo.api.gouv.fr/communes`;

@Injectable({
  providedIn: 'root'
})
export class VillesService {

  constructor(private http: HttpClient) {
  }

  search(text:string) {


    return this.http.get(URL_GET_API_GEOPORTAIL_SEARCH_COMMUNES,{
      params:new HttpParams()
        .set('nom',text)
        .set('fields','codesPostaux')
        .set('limit','10')
    }).pipe(
      catchError(err => of(err))
    )
  }
}

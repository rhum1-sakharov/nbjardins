import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, filter, switchMap} from "rxjs/operators";
import {of} from "rxjs/internal/observable/of";
import {MSG_KEY, MSG_SEVERITY, ToasterService} from "./toaster.service";
import {MError} from "../../models/m-error";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  private manageErrors$ = (changes: Observable<any>) => changes.pipe(
    catchError(err => this.handleTechnicalError(err)),
    filter(response => !(response instanceof MError)),
    switchMap(response => this.handleServerError(response)),
    filter((response: any) =>  {
      if(response && response.error){
        return response.error=false;
      }
      return response;
    })
  );

  constructor(private http: HttpClient, private toastSvc: ToasterService) {
  }

  get(url: string, params: HttpParams = null) {
    return this.http.get(url, {
      params: params
    }).pipe(this.manageErrors$)
  }

  post(url: string, body, params: HttpParams = null) {
    return this.http.post(url, body, {
      params: params
    }).pipe(this.manageErrors$);
  }

  handleTechnicalError(err: HttpErrorResponse) {
    this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, 'Erreur interne.', err.message);
    return of(new MError());
  }

  handleServerError(response: any) {

    if (response.error) {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, response.errorMessages.map(item => item).join(' '));
    }
    return of(response);

  }

}

import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from "@angular/common/http";
import {catchError, delay, filter, finalize, switchMap} from "rxjs/operators";
import {of} from "rxjs/internal/observable/of";
import {MSG_KEY, MSG_SEVERITY, ToasterService} from "./toaster.service";
import {MError} from "../../models/m-error";
import {Observable} from "rxjs/internal/Observable";
import {LoadingService} from './loading.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  private manageErrors$ = (changes: Observable<any>) => changes.pipe(
    delay(5),
    catchError(err => this.handleTechnicalError(err)),
    filter(response => !(response instanceof MError)),
    switchMap(response => this.handleServerError(response)),
    filter((response: any) => response.errorMessages.length===0),
    finalize(()=>this.loadingSvc.loading=false)
  );

  constructor(private http: HttpClient, private toastSvc: ToasterService, private loadingSvc: LoadingService) {
  }

  get(url: string, params: HttpParams = null,loading=true) {

    this.loadingSvc.loading=loading;

    return this.http.get(url, {
      params: params
    }).pipe(this.manageErrors$)
  }

  post(url: string, body, params: HttpParams = null,loading=true) {

    this.loadingSvc.loading=loading;

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

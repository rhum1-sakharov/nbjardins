import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams} from '@angular/common/http';
import {catchError, delay, filter, finalize} from 'rxjs/operators';
import {MSG_KEY, MSG_SEVERITY, ToasterService} from './toaster.service';
import {LoadingService} from './loading.service';
import {MError} from '../models/m-error';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpService {


  private manageErrors$ = (changes: Observable<any>) => changes.pipe(
    delay(5),
    catchError(err => this.handleError(err)),
    filter(response => !(response instanceof MError)),
    finalize(() => this.loadingSvc.loading = false)
  )

  constructor(private http: HttpClient, private toastSvc: ToasterService, private loadingSvc: LoadingService) {
  }

  get(url: string, params: HttpParams, loading = true): any {

    this.loadingSvc.loading = loading;

    return this.http.get(url, {params}).pipe(this.manageErrors$);
  }

  post(url: string, body: any, params: HttpParams, loading = true): any {

    this.loadingSvc.loading = loading;

    return this.http.post(url, body, {params}).pipe(this.manageErrors$);
  }

  handleError(err: HttpErrorResponse): Observable<MError> {

    if (err.error ) {
      if (err.error.errorMessages) {
        this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, 'Traitement en erreur.',
          err.error.errorMessages.map((item: string) => item).join(' '));
      } else {
        this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, 'Erreur interne.', err.message);
      }
    }
    return of(new MError());
  }

  handleServerError(response: any): Observable<MError> {

    if (response.error) {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.ERROR, response.errorMessages.map((item: string) => item).join(' '));
    }
    return of(response);

  }

}

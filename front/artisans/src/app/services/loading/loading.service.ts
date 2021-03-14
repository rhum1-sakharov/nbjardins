import {Injectable} from '@angular/core';
import {NavigationStart, ResolveStart, Router, RoutesRecognized} from '@angular/router';
import {BehaviorSubject, combineLatest, Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private subjectLoading = new BehaviorSubject(false);
  specifiLoading$ = this.subjectLoading.asObservable();
  loading$ !: Observable<boolean>;

  constructor(private router: Router) {

    this.loading$ = combineLatest([this.router.events, this.specifiLoading$]).pipe(
      map(data => data[0] instanceof NavigationStart ||
        data[0] instanceof RoutesRecognized || data[0] instanceof ResolveStart || data[1] && data[1] == true));

  }



  announceLoading(loading: boolean) {
    this.subjectLoading.next(loading);
  }
}

import {Injectable} from '@angular/core';
import {Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  loading = false;

  constructor() {
  }

  unsubscribe(sub: Subscription) {
    if (sub) {
      sub.unsubscribe();
    }
  }
}

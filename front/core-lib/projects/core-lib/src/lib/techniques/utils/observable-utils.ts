import {Subscription} from 'rxjs';


export class ObservableUtils {

  public static unsubscribe(sub: Subscription) {
    if (sub) {
      sub.unsubscribe();
    }
  }
}

import {Injectable} from '@angular/core';
import {Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  loading = false;

  constructor() {
  }

  isCollectionNoe(collection:any[]) : boolean{
    if(!collection || collection.length===0){
      return true;
    }

    return false;
  }

  preselectSingleElement(elements: any[], selectedElement: any): any {

    if (elements && selectedElement) {
      for (const elt of elements) {
        if (elt.id === selectedElement.id) {
          return elt;
        }
      }
    }
    return null;
  }

  unsubscribe(sub: Subscription) {
    if (sub) {
      sub.unsubscribe();
    }
  }
}

import {Injectable} from '@angular/core';
import * as SecureLS from 'secure-ls';

@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {


  /**
   * Crypt / Decrypt localstorage keys/values
   */
  secureLS: SecureLS;

  constructor() {

    this.secureLS = new SecureLS({encodingType: 'des', isCompression: false, encryptionSecret: 'aBzE3_1q'});
  }

  setItem(key: string, value: any) {

    this.secureLS.set(key, value);
  }

  getItem(key: string): any {
    let item = null;
    try{
      item = this.secureLS.get(key);
    }catch(e){
      console.error(e);
    }
    return item;
  }
}

import {Injectable} from '@angular/core';
import {MessageService} from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class ToasterService {

  constructor(private msgSvc: MessageService) {
  }

  showMsg(key: MSG_KEY, severity: MSG_SEVERITY, msg: string, detail: string = '', life: number = 8000): void {

    if (MSG_SEVERITY.SUCCESS === severity) {
      msg = '😊 ' + msg;
    }


    this.msgSvc.add({key, severity, summary: msg, detail, life});
  }
}

export enum MSG_KEY {
  ROOT = 'root'
}

export enum MSG_SEVERITY {
  SUCCESS = 'success',
  INFO = 'info',
  WARN = 'warn',
  ERROR = 'error'

}

export enum MSG_POSITION {

  BOTTOM_CENTER = 'bottom-center',
  TOP_CENTER = 'top-center',
  TOP_LEFT = 'top-left'
}

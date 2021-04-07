import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DevisAnnouncesService {

  private subjectOpenDialogCreateDevis = new Subject<OpenDialogCreateDevisSupplier>();
  openDialogCreateDevis$ = this.subjectOpenDialogCreateDevis.asObservable();

  constructor() {
  }

  announceOpenDialogCreateDevis(odcd: OpenDialogCreateDevisSupplier) {
    this.subjectOpenDialogCreateDevis.next(odcd);
  }
}

export class OpenDialogCreateDevisSupplier {

  emailArtisan !: string;


  constructor(emailArtisan: string) {
    this.emailArtisan = emailArtisan;
  }
}

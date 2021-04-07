import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {MDevis} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class DevisAnnouncesService {

  private subjectOpenDialogCreateDevis = new Subject<OpenDialogCreateDevisSupplier>();
  openDialogCreateDevis$ = this.subjectOpenDialogCreateDevis.asObservable();

  private subjectCloseDialogCreateDevis = new Subject<MDevis>();
  closeDialogCreateDevis$ = this.subjectCloseDialogCreateDevis.asObservable();

  private subjectDevisRemoved = new Subject<MDevis>();
  devisRemoved$ = this.subjectDevisRemoved.asObservable();

  constructor() {
  }

  announceOpenDialogCreateDevis(odcd: OpenDialogCreateDevisSupplier) {
    this.subjectOpenDialogCreateDevis.next(odcd);
  }

  announceCloseDialogCreateDevis(devis: MDevis) {
    this.subjectCloseDialogCreateDevis.next(devis);
  }

  announceDevisRemoved(devis: MDevis) {
    this.subjectDevisRemoved.next(devis);
  }
}

export class OpenDialogCreateDevisSupplier {

  emailArtisan !: string;


  constructor(emailArtisan: string) {
    this.emailArtisan = emailArtisan;
  }
}

import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {MDevis} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class DevisAnnouncesService {

  private subjectOpenDialogCreateDevis = new Subject<OpenDialogCreateDevisSupplier>();
  openDialogCreateDevis$ = this.subjectOpenDialogCreateDevis.asObservable();

  private subjectDevisCreated = new Subject<MDevis>();
  devisCreated$ = this.subjectDevisCreated.asObservable();

  private subjectDevisRemoved = new Subject<MDevis>();
  devisRemoved$ = this.subjectDevisRemoved.asObservable();

  private subjectDevisSelected = new BehaviorSubject(new MDevis());
  devisSelected$ = this.subjectDevisSelected.asObservable();

  constructor() {
  }

  announceOpenDialogCreateDevis(odcd: OpenDialogCreateDevisSupplier) {
    this.subjectOpenDialogCreateDevis.next(odcd);
  }

  announceDevisCreated(devis: MDevis) {
    this.subjectDevisCreated.next(devis);
  }

  announceDevisRemoved(devis: MDevis) {
    this.subjectDevisRemoved.next(devis);
  }

  announceDevisSelected(devis: MDevis) {
    this.subjectDevisSelected.next(devis);
  }
}

export class OpenDialogCreateDevisSupplier {

  emailArtisan !: string;


  constructor(emailArtisan: string) {
    this.emailArtisan = emailArtisan;
  }
}

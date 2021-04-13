import {Injectable} from '@angular/core';
import {MDevis, MDevisOption} from 'rhum1-sakharov-core-lib';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DesignerAnnouncesService {

  private subjectOpenDialogBlockArtisan = new Subject<OpenDialogBlockArtisanSupplier>();
  openDialogBlockArtisan$ = this.subjectOpenDialogBlockArtisan.asObservable();

  private subjectOpenDialogBlockClient = new Subject<OpenDialogBlockClientSupplier>();
  openDialogBlockClient$ = this.subjectOpenDialogBlockClient.asObservable();

  private subjectOpenDialogDevisOptions = new Subject<OpenDialogDevisOptionsSupplier>();
  openDialogDevisOptions$ = this.subjectOpenDialogDevisOptions.asObservable();

  constructor() { }

  announceOpenDialogDevisOptions(oddo: OpenDialogDevisOptionsSupplier) {
    this.subjectOpenDialogDevisOptions.next(oddo);
  }


  announceOpenDialogBlockArtisan(odba: OpenDialogBlockArtisanSupplier) {
    this.subjectOpenDialogBlockArtisan.next(odba);
  }

  announceOpenDialogBlockClient(odbc: OpenDialogBlockClientSupplier) {
    this.subjectOpenDialogBlockClient.next(odbc);
  }
}

export class OpenDialogBlockArtisanSupplier {
  devis !: MDevis;
  devisOptionList !: MDevisOption[];


  constructor(devis: MDevis, devisOptionList: MDevisOption[]) {
    this.devis = devis;
    this.devisOptionList = devisOptionList;
  }
}

export class OpenDialogBlockClientSupplier {
  devis !: MDevis;
  devisOptionList !: MDevisOption[];


  constructor(devis: MDevis, devisOptionList: MDevisOption[]) {
    this.devis = devis;
    this.devisOptionList = devisOptionList;
  }
}

export class OpenDialogDevisOptionsSupplier {
  idDevis !: string;


  constructor(idDevis: string) {
    this.idDevis = idDevis;
  }
}

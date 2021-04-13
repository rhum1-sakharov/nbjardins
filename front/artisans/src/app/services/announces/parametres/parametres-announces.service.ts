import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {MArtisan, MArtisanBanque, MArtisanOption} from 'rhum1-sakharov-core-lib';

@Injectable({
  providedIn: 'root'
})
export class ParametresAnnouncesService {

  private subjectSupplier = new Subject();
  supplier$ = this.subjectSupplier.asObservable();

  constructor() {
  }

  announcePreviewDevis(pa: ParametresAnnounce) {
    this.subjectSupplier.next(pa);
  }
}

export class ParametresAnnounce {
  artisan !: MArtisan;
  artisanBanque !: MArtisanBanque;
  artisanOptionList !: MArtisanOption[];

}

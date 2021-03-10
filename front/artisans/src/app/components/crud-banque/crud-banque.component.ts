import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MArtisan, MArtisanBanque, UtilsService} from 'rhum1-sakharov-core-lib';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-crud-banque',
  templateUrl: './crud-banque.component.html',
  styleUrls: ['./crud-banque.component.scss']
})
export class CrudBanqueComponent implements OnInit {

  @Input() artisanBanqueList !: MArtisanBanque[];
  @Input() artisan !: MArtisan;
  @Output() outArtisanBanqueList = new EventEmitter();

  display = false;

  form !: FormGroup;
  warnMsg: string = '';

  constructor(public utils: UtilsService) {
  }

  initForm() {
    this.form = new FormGroup({
      banque: new FormControl('', Validators.required),
      iban: new FormControl('', Validators.required),
      rib: new FormControl('', Validators.required),
      prefere: new FormControl(false, Validators.required),
    });
  }

  ngOnInit(): void {

    this.initForm();

  }

  addBanque() {

    this.display = true;
    this.initForm();

  }

  close() {
    this.display = false;
    this.warnMsg = '';
  }

  save() {

    if (!this.form.valid) {
      this.warnMsg = 'Tous les champs sont obligatoires.';
      return;
    }


    let banque = new MArtisanBanque();
    banque.id='';
    banque.prefere = this.form.controls['prefere'].value;
    banque.iban = this.form.controls['iban'].value;
    banque.rib = this.form.controls['rib'].value;
    banque.banque = this.form.controls['banque'].value;
    banque.artisan = this.artisan;

    if (this.utils.isCollectionNoe(this.artisanBanqueList)) {
      this.artisanBanqueList = [];
      banque.prefere = true;
      this.artisanBanqueList.push(banque);
    } else {


      this.artisanBanqueList.push(banque);

      this.updateBanquePrefere(banque, this.artisanBanqueList, banque.prefere);

    }

    this.outArtisanBanqueList.emit(this.artisanBanqueList);

    this.close();

  }

  updateBanquePrefere(banque: MArtisanBanque, artisanBanqueList: MArtisanBanque[], prefere: boolean) {

    if (prefere) {

      banque.prefere = prefere;

      artisanBanqueList.forEach(item => {
        if (item !== banque) {
          item.prefere = false;
        }
      })

      this.outArtisanBanqueList.emit(artisanBanqueList);

    }


  }

  removeBanque(banque: MArtisanBanque) {
    this.artisanBanqueList = this.artisanBanqueList.filter(item=>item !== banque);
    this.outArtisanBanqueList.emit(this.artisanBanqueList);
  }
}

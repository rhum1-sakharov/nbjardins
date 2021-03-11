import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {
  AbstractCrud,
  CRUD_MODE,
  MArtisan,
  MArtisanBanque,
  MSG_KEY,
  MSG_SEVERITY,
  ToasterService,
  UtilsService
} from 'rhum1-sakharov-core-lib';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Error} from 'tslint/lib/error';

@Component({
  selector: 'app-crud-banque',
  templateUrl: './crud-banque.component.html',
  styleUrls: ['./crud-banque.component.scss']
})
export class CrudBanqueComponent extends AbstractCrud<MArtisanBanque> implements OnInit {

  @Input() artisanBanqueList !: MArtisanBanque[];
  @Input() artisan !: MArtisan;
  @Output() outArtisanBanqueList = new EventEmitter();

  display = false;

  form !: FormGroup;
  warnMsg: string = '';


  constructor(public utils: UtilsService, private toastSvc: ToasterService) {
    super();
  }

  ngOnInit(): void {
    this.initForm(CRUD_MODE.ADD, null);
  }

  initForm(mode: CRUD_MODE, model: MArtisanBanque | null): void {
    switch (mode) {
      case CRUD_MODE.ADD :


        this.form = new FormGroup({
          id: new FormControl(''),
          banque: new FormControl('', Validators.required),
          iban: new FormControl('', Validators.required),
          rib: new FormControl('', Validators.required),
          prefere: new FormControl(false, Validators.required),
          artisan: new FormControl(this.artisan, Validators.required),
        });
        break;
      case CRUD_MODE.EDIT :
        if (!model) {
          throw new Error('model is null, cannot init form in edit mode');
        }
        this.form = new FormGroup({
          id: new FormControl(model.id, Validators.required),
          banque: new FormControl(model.banque, Validators.required),
          iban: new FormControl(model.iban, Validators.required),
          rib: new FormControl(model.rib, Validators.required),
          prefere: new FormControl(model.prefere, Validators.required),
          artisan: new FormControl(model.artisan, Validators.required),
        });
        break;
    }
  }

  openDialog(crudMode: CRUD_MODE, artisanBanque: MArtisanBanque | null) {
    this.display = true;
    this.selectedCrudMode = crudMode;

    this.initForm(this.selectedCrudMode, artisanBanque);

  }


  save(crudMode: CRUD_MODE) {

    if (!this.form.valid) {
      this.warnMsg = 'Tous les champs sont obligatoires.';
      return;
    }

    let artisanBanque = new MArtisanBanque();
    artisanBanque.id = this.form.controls['id'].value;
    artisanBanque.prefere = this.form.controls['prefere'].value;
    artisanBanque.iban = this.form.controls['iban'].value;
    artisanBanque.rib = this.form.controls['rib'].value;
    artisanBanque.banque = this.form.controls['banque'].value;
    artisanBanque.artisan = this.artisan;

    switch (crudMode) {
      case CRUD_MODE.ADD:
        this.artisanBanqueList = [...this.create(artisanBanque, this.artisanBanqueList)];
        break;
      case CRUD_MODE.EDIT:
        this.artisanBanqueList = [...this.update(artisanBanque, this.artisanBanqueList)];
        break;

    }

    this.updateBanquePrefere(artisanBanque, this.artisanBanqueList, artisanBanque.prefere);

    this.outArtisanBanqueList.emit(this.artisanBanqueList);

    this.closeDialog();

  }

  create(artisanBanque: MArtisanBanque, artisanBanqueList: MArtisanBanque[]) {

    let abList = artisanBanqueList;
    if (this.utils.isCollectionNoe(abList)) {
      abList = [];
    }
    abList.push(artisanBanque);

    return abList;

  }

  update(artisanBanque: MArtisanBanque, artisanBanqueList: MArtisanBanque[]): MArtisanBanque[] {

    const abList = [];
    for (const ab of artisanBanqueList) {
      if (ab.id === artisanBanque.id) {
        abList.push(artisanBanque);
        break;
      } else {
        abList.push(ab);
      }
    }
    return abList;
  }

  updateBanquePrefere(banque: MArtisanBanque, artisanBanqueList: MArtisanBanque[], prefere: boolean) {

    // si une seule banque, c'est forcement le prefere
    if (artisanBanqueList.length === 1) {
      banque.prefere = true;
      if (prefere === false) {
        this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.WARN, `Il n'y a qu'une seule banque, elle est donc forcement la préférée.`);
      }
    }
    else if (prefere) {

      banque.prefere = prefere;

      artisanBanqueList.forEach(item => {
        if (item !== banque) {
          item.prefere = false;
        }
      })
    }

    this.outArtisanBanqueList.emit(artisanBanqueList);

  }

  removeBanque(banque: MArtisanBanque) {
    this.artisanBanqueList = this.artisanBanqueList.filter(item => item !== banque);
    this.outArtisanBanqueList.emit(this.artisanBanqueList);
  }

  closeDialog(): void {

    this.display = false;
    this.warnMsg = '';
  }


}

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {
  AbstractCrud,
  CollectionUtils,
  CRUD_MODE,
  MArtisan,
  MArtisanBanque,
  MSG_KEY,
  MSG_SEVERITY,
  ToasterService
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
  selectedArtisanBanque !: MArtisanBanque | null;


  constructor(private toastSvc: ToasterService) {
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
          id: new FormControl(model.id),
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
    this.selectedArtisanBanque = artisanBanque || null;

    this.initForm(this.selectedCrudMode, this.selectedArtisanBanque);

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
    artisanBanque.artisan = this.form.controls['artisan'].value;

    switch (crudMode) {
      case CRUD_MODE.ADD:
        this.artisanBanqueList = [...CollectionUtils.addElement(artisanBanque, this.artisanBanqueList)];
        break;
      case CRUD_MODE.EDIT:
        this.artisanBanqueList = [...CollectionUtils.updateElement(artisanBanque, 'id', this.artisanBanqueList)];
        break;

    }

    this.updateBanquePrefere(artisanBanque, this.artisanBanqueList, artisanBanque.prefere);

    this.closeDialog();

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

  removeArtisanBanque(artisanBanque: MArtisanBanque | null) {

    if (artisanBanque && artisanBanque.prefere) {
      this.toastSvc.showMsg(MSG_KEY.ROOT, MSG_SEVERITY.WARN, 'Vous ne pouvez pas supprimer votre banque préférée.');
    }else{
      this.artisanBanqueList = this.artisanBanqueList.filter(item => item !== artisanBanque);
    }


    this.closeDialog();
  }

  closeDialog(): void {

    this.display = false;
    this.warnMsg = '';
    this.selectedArtisanBanque = null;
    this.outArtisanBanqueList.emit(this.artisanBanqueList);
  }


  onRowSelect(event: any) {
    this.openDialog(CRUD_MODE.EDIT, event.data);
  }

}

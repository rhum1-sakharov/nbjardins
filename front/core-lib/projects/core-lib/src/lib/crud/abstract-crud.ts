import {CRUD_MODE} from '../enums/e-crud-mode';

export abstract class AbstractCrud<M> {

  public crudMode = CRUD_MODE;
  public selectedCrudMode !: CRUD_MODE;

  abstract initForm(mode: CRUD_MODE, model: M | null): void;

  abstract openDialog(mode: CRUD_MODE,  model: M | null): void;

  abstract closeDialog(): void;

}


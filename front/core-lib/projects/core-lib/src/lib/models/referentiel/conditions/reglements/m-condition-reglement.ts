import {Model} from '../../../model';

export class MConditionReglement extends Model {

  id: string;
  condition: string;

  constructor(id: string, condition: string) {
    super();
    this.id = id;
    this.condition = condition;
  }
}

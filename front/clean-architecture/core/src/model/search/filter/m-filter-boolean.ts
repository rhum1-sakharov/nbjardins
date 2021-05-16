import {MFilter} from './m-filter';
import {OPERATOR_BOOLEAN} from '../../../enum/search/operator/e-operator-boolean';

export class MFilterBoolean extends MFilter {

  operator !: OPERATOR_BOOLEAN;
  value: boolean = false;

}

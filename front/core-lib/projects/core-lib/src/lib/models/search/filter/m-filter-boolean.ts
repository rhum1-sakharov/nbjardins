import {MFilter} from './m-filter';
import {OPERATOR_STRING} from '../../../enums/search/operator/e-operator-string';
import {OPERATOR_BOOLEAN} from '../../../enums/search/operator/e-operator-boolean';

export class MFilterBoolean extends MFilter {

  operator !: OPERATOR_BOOLEAN;
  value: boolean = false;

}

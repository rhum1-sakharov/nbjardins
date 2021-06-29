import {MFilter} from './m-filter';
import {OPERATOR_STRING} from '../../../enum/search/operator/e-operator-string';

export class MFilterString extends MFilter {

  operator !: OPERATOR_STRING;
  value: string[] = [];

}

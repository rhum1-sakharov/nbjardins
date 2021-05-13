import {MFilter} from './m-filter';
import {OPERATOR_NUMBER} from '../../../enums/search/operator/e-operator-number';

export class MFilterNumber extends MFilter {

  operator !: OPERATOR_NUMBER;
  value: number[] = [];

}

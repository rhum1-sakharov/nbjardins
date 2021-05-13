import {MFilter} from './m-filter';
import {OPERATOR_DATE} from '../../../enums/search/operator/e-operator-date';

export class MFilterDate extends MFilter {

  operator !: OPERATOR_DATE;
  value: Date[] = [];

}

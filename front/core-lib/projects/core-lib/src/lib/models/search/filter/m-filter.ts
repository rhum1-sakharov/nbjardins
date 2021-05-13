import {FILTER_TYPE} from '../../../enums/search/e-filter-type';
import {FILTER_JOIN} from '../../../enums/search/e-filter-join';

export class MFilter {

  key: string = '';
  type !: FILTER_TYPE;
  join: FILTER_JOIN = FILTER_JOIN.AND;


}

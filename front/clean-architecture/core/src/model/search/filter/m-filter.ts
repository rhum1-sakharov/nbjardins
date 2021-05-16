import {FILTER_TYPE} from '../../../enum/search/e-filter-type';
import {FILTER_JOIN} from '../../../enum/search/e-filter-join';

export class MFilter {

  key: string = '';
  type !: FILTER_TYPE;
  join: FILTER_JOIN = FILTER_JOIN.AND;


}

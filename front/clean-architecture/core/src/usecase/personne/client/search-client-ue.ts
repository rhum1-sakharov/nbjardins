import {AbstractUsecase} from '../../abstract-usecase';
import {MSearch} from '../../../model/search/m-search';
import {SearchResponseML} from '../../../model/search/search-response-ml';
import {ParameterUtil} from '../../../utils/parameter-util';

export class SearchClientUE extends AbstractUsecase {

    execute(search: MSearch) : SearchResponseML {

        ParameterUtil.isNotNull([search],['search']);

        return null;
    }



}
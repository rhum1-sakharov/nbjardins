import {MSearch} from '../../../../model/search/m-search';
import {SearchResponseML} from '../../../../model/search/search-response-ml';

export interface ClientRepoPT {

    searchClient(search: MSearch): Promise<SearchResponseML>;

}
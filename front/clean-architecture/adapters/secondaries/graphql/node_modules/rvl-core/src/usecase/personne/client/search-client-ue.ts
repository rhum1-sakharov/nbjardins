import {AbstractUsecase} from '../../abstract-usecase';
import {MSearch} from '../../../model/search/m-search';
import {SearchResponseML} from '../../../model/search/search-response-ml';
import {ParameterUtil} from '../../../utils/parameter-util';
import {ClientRepoPT} from '../../../port/repository/personne/client/client-repo-pt';

export class SearchClientUE extends AbstractUsecase {

    clientRepo: ClientRepoPT;

    constructor(clientRepo: ClientRepoPT) {
        super();
        this.clientRepo = clientRepo;
    }

     async execute(search: MSearch): Promise<SearchResponseML> {

        ParameterUtil.isNotNull([search], ['search']);

        return await this.clientRepo.searchClient(search);

    }


}
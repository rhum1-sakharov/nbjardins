import {AbstractUsecase} from '../../abstract-usecase';
import {MSearch} from '../../../model/search/m-search';
import {SearchResponseML} from '../../../model/search/search-response-ml';
import {FeatureError} from '../../../exceptions/FeatureError';

export class SearchClientUE extends AbstractUsecase {

    execute(search: MSearch) : SearchResponseML {

        this.checkParameters(search);

        console.log('searchClientUE', search);

        return null;
    }

    private  checkParameters(search:MSearch){

        if(!search){
            throw new FeatureError('search arg is required');
        }

    }

}
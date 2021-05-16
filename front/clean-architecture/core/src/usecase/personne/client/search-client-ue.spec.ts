import * as chai from 'chai';
import {SearchClientUE} from './search-client-ue';
import {FeatureError} from '../../../exceptions/FeatureError';

const expect = chai.expect;
const assert = chai.assert;

describe('search client', () => {

    const usecase = new SearchClientUE();

    it('should have search arg not null' , () => {

        assert.throw(()=> usecase.execute(null),FeatureError,"search arg is required");

    });

});
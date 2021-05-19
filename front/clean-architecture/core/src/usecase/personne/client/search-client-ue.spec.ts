import * as mocha from 'mocha';
import * as chai from 'chai';
import {SearchClientUE} from './search-client-ue';
import {FeatureError} from '../../../exceptions/FeatureError';
import {ClientRepoPT} from '../../../port/repository/personne/client/client-repo-pt';

const expect = chai.expect;
const assert = chai.assert;
const it = mocha.it;
const describe = mocha.describe;

describe('search client', () => {

    const clientRepo: ClientRepoPT = null;
    const usecase = new SearchClientUE(clientRepo);

    it('should have search arg not null', () => {

        assert.throw(() => usecase.execute(null), FeatureError, 'search argument is required');

    });

});
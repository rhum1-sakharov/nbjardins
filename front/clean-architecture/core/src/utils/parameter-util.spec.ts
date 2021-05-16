import * as chai from 'chai';
import {ParameterUtil} from './parameter-util';
import {FeatureError} from '../exceptions/FeatureError';
import {TechnicalError} from '../exceptions/TechnicalError';

const expect = chai.expect;
const assert = chai.assert;

describe('parameter util, isNotNull', () => {

    it('should have params arg not null' , () => {
        assert.throw(()=> ParameterUtil.isNotNull(null,null),TechnicalError,"params arg is required");
    });

    it('should have  argNames not null' , () => {

        const params=[null,1];
        assert.throw(()=> ParameterUtil.isNotNull(params,null),TechnicalError,"argNames arg is required");
    });

    it('should have argNames and params same length' , () => {

        const params=[null,1];
        const argNames=['1'];
        assert.throw(()=> ParameterUtil.isNotNull(params,argNames),TechnicalError,"params and argNames should have same length");
    });

    it(`when params[0] is null, should display 'argName[0] argument is required'` , () => {

        const params=[null,2];
        const argNames=['arg search','arg isOk'];

        assert.throw(()=> ParameterUtil.isNotNull(params,argNames),FeatureError,`${argNames[0]} argument is required`);
    });



});
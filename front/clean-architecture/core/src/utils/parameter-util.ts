import {FeatureError} from '../exceptions/FeatureError';
import {TechnicalError} from '../exceptions/TechnicalError';

export class ParameterUtil{

    public static isNotNull(params:any[],argNames:string[]):boolean{

        if(!params){
            throw new TechnicalError('params arg is required');
        }

        if(!argNames){
            throw new TechnicalError('argNames arg is required');
        }

        if(argNames.length != params.length ){
            throw new TechnicalError('params and argNames should have same length');
        }

        let errMsg='';
        for (let i=0;i<params.length;i++) {
            if(!params[i]){
                errMsg += `${argNames[i]} argument is required
                `;
            }
        }

        if(errMsg){
            throw new FeatureError(errMsg);
        }

        return true;
    }

}
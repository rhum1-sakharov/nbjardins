import {ClientRepoPT, MSearch, SearchResponseML, ParameterUtil} from 'rvl-core';
import {AbstractRepo} from '../../abstract-repo';
import {ConfigML} from '../../..';
import axios from 'axios';


export class ClientRepoGL extends AbstractRepo implements ClientRepoPT {

    constructor(config: ConfigML) {
        super(config);
    }

    async searchClient(search: MSearch): Promise<SearchResponseML> {

        console.log('in clientrepogl', this.config);

        ParameterUtil.isNotNull([search], ['search']);

        const response = await axios.post(`${this.config.rootUrl}`, search);

        return response.data;


    }

}
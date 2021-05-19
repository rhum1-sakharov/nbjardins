import {ClientRepoPT, MSearch, SearchResponseML} from 'rvl-core';

export class ClientRepoGL implements ClientRepoPT {

    searchClient(search: MSearch): SearchResponseML {
        console.log('in clientrepogl');
        return null;
    }

}
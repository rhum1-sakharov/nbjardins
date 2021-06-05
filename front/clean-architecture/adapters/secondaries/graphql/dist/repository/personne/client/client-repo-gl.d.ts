import { ClientRepoPT, MSearch, SearchResponseML } from 'rvl-core';
import { AbstractRepo } from '../../abstract-repo';
import { ConfigML } from '../../..';
export declare class ClientRepoGL extends AbstractRepo implements ClientRepoPT {
    constructor(config: ConfigML);
    searchClient(search: MSearch): Promise<SearchResponseML>;
}

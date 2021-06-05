import {SearchClientUE, MSearch} from 'rvl-core';
import {ClientRepoGL} from 'rvl-graphql';


const clientRepo = new ClientRepoGL({rootUrl: 'api/graphql'});
const sc = new SearchClientUE(clientRepo);

const search = new MSearch();
sc.execute(search).then(response => console.log(response));
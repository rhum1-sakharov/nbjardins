import {SearchClientUE, MSearch} from 'rvl-core';
import {ClientRepoGL} from 'rvl-graphql';


const clientRepo = new ClientRepoGL();
const sc = new SearchClientUE(clientRepo);

const search = new MSearch();
sc.execute(search);
import {TEST_CONFIG} from '../../../model/config-m-l';
import {ClientRepoGL} from './client-repo-gl';

describe('ClientRepoGL', () => {

    const clientRepo: ClientRepoGL = new ClientRepoGL(TEST_CONFIG);

    test('throw error when search argument is null', async () => {

        await expect(() => clientRepo.searchClient(null))
            .rejects
            .toThrow('search argument is required');

    });

});
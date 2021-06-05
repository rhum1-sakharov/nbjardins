export class ConfigML {

    rootUrl: string;

    constructor(rootUrl: string) {
        this.rootUrl = rootUrl;
    }
}

export const TEST_CONFIG: ConfigML = new ConfigML('http://localhost:8080/api/graphql');
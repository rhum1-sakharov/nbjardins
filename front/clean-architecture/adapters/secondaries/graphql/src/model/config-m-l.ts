export class ConfigML {

    rootUrl: string;

    constructor(rootUrl: string) {
        this.rootUrl = rootUrl;
    }
}

export const testConfig: ConfigML = new ConfigML('http://localhost:8080/api/graphql');
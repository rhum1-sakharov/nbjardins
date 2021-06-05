import {ConfigML} from '..';

export abstract class AbstractRepo {

    protected config: ConfigML;

    constructor(config: ConfigML) {
        this.config = config;
    }
}
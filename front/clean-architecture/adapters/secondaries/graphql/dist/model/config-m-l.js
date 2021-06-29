"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.TEST_CONFIG = exports.ConfigML = void 0;
class ConfigML {
    constructor(rootUrl) {
        this.rootUrl = rootUrl;
    }
}
exports.ConfigML = ConfigML;
exports.TEST_CONFIG = new ConfigML('http://localhost:8080/api/graphql');
//# sourceMappingURL=config-m-l.js.map
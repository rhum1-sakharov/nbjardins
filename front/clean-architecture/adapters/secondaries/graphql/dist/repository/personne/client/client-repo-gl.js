"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ClientRepoGL = void 0;
const rvl_core_1 = require("rvl-core");
const abstract_repo_1 = require("../../abstract-repo");
const axios_1 = require("axios");
class ClientRepoGL extends abstract_repo_1.AbstractRepo {
    constructor(config) {
        super(config);
    }
    searchClient(search) {
        return __awaiter(this, void 0, void 0, function* () {
            console.log('in clientrepogl', this.config);
            rvl_core_1.ParameterUtil.isNotNull([search], ['search']);
            const response = yield axios_1.default.post(`${this.config.rootUrl}`, search);
            return response.data;
        });
    }
}
exports.ClientRepoGL = ClientRepoGL;
//# sourceMappingURL=client-repo-gl.js.map
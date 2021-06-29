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
const config_m_l_1 = require("../../../model/config-m-l");
const client_repo_gl_1 = require("./client-repo-gl");
describe('ClientRepoGL', () => {
    const clientRepo = new client_repo_gl_1.ClientRepoGL(config_m_l_1.TEST_CONFIG);
    test('throw error when search argument is null', () => __awaiter(void 0, void 0, void 0, function* () {
        yield expect(() => clientRepo.searchClient(null))
            .rejects
            .toThrow('search argument is required');
    }));
});
//# sourceMappingURL=client-repo-gl.spec.js.map
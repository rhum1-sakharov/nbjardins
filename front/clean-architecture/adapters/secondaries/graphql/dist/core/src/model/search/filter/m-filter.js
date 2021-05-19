"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.MFilter = void 0;
var e_filter_join_1 = require("../../../enum/search/e-filter-join");
var MFilter = /** @class */ (function () {
    function MFilter() {
        this.key = '';
        this.join = e_filter_join_1.FILTER_JOIN.AND;
    }
    return MFilter;
}());
exports.MFilter = MFilter;

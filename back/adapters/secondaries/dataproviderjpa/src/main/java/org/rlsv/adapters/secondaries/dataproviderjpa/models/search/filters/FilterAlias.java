package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters;

import enums.search.filter.OPERATOR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.search.filter.Filter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterAlias extends Filter {

    private String alias;

    public FilterAlias(String key, OPERATOR operator, String value, List<String> idList, String alias) {
        super(key, operator, value, idList);
        this.alias = alias;
    }
}

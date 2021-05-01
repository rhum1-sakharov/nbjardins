package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import models.search.filter.Filter;

@Getter
@Setter
@SuperBuilder
public class FilterPath<T extends Filter> {

    private String path;
    private T filter;


}

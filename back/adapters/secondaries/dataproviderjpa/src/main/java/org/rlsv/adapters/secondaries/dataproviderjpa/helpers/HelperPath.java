package org.rlsv.adapters.secondaries.dataproviderjpa.helpers;

import models.search.filter.Filter;
import models.search.sort.Sort;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.SortPath;

import java.util.List;


public abstract class HelperPath {

    public abstract String selectLine();

    public abstract String countLine();

    public abstract List<FilterPath> buildFilterPathList(List<Filter> filters);

    public abstract List<SortPath> buildSortPathList(List<Sort> sorts);
}

package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import models.search.sort.Sort;

@Getter
@Setter
@SuperBuilder
public class SortPath{

    private String path;
    private Sort sort;


}

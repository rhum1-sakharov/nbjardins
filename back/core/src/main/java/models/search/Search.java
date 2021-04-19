package models.search;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import models.search.filter.Filter;
import models.search.page.Page;
import models.search.sort.Sort;

import java.util.List;

@Getter
@Setter
@Builder
public class Search {

    private Page page;
    private List<Filter> filters;
    private List<Sort> sorts;


}

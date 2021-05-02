package models.search.filter;


import enums.search.filter.FILTER_BOOL_JOIN;
import enums.search.filter.FILTER_TYPE;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Filter {

    private String key;
    private FILTER_TYPE type;
    private FILTER_BOOL_JOIN boolJoin;


}

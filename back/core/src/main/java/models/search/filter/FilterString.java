package models.search.filter;


import enums.search.filter.OPERATOR_STRING;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FilterString extends Filter {

    private OPERATOR_STRING operator;
    private String[] value;


}

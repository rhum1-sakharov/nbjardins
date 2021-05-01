package models.search.filter;


import enums.search.filter.OPERATOR_BOOLEAN;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FilterBoolean extends Filter {

    private OPERATOR_BOOLEAN operator;
    private Boolean value;


}

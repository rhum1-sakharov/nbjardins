package models.search.filter;


import enums.search.filter.OPERATOR_NUMBER;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class FilterNumber  extends Filter{

    private OPERATOR_NUMBER operator;
    private float[] value;


}

package models.search.filter;


import enums.search.filter.OPERATOR_DATE;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class FilterDate extends Filter {

    private OPERATOR_DATE operator;
    private LocalDate[] value;


}

package models.search.filter;


import enums.search.filter.OPERATOR;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

    private String key;
    private OPERATOR operator;
    private String value;


}

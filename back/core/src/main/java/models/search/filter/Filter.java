package models.search.filter;


import enums.search.filter.OPERATOR;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Filter {

   private String key;
   private OPERATOR operator;
   private String value;

}

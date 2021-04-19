package models.search.filter;


import enums.search.filter.OPERATOR;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter {

   private String key;
   private OPERATOR operator;
   private String value;

}

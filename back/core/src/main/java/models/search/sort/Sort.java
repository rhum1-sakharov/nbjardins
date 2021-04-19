package models.search.sort;


import enums.search.sort.DIRECTION;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sort {

   private String key;
   private DIRECTION direction;

}

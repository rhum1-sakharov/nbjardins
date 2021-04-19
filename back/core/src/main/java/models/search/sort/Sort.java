package models.search.sort;


import enums.search.sort.DIRECTION;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Sort {

   private String key;
   private DIRECTION direction;

}

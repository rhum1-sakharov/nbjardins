package models.search.page;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Page {

    private int pageIngex;
    private int pageSize;

}

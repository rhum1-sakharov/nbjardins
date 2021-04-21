package models.search.response;

import domains.Domain;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse<D extends Domain> {

    private List<D> resultList;
    private int totalElements;

}

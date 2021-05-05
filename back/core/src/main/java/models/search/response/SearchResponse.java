package models.search.response;

import domains.Domain;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchResponse<D extends Domain> {

    private List<D> resultList;
    private long totalElements;

}

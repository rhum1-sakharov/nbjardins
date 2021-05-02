package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Path {

    private String path;
    private String field;
    private boolean rootPath;
}

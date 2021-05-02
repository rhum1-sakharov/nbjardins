package org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.SqlJoin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Path {

    private String path;
    private String field;
    private boolean rootPath;
    private SqlJoin sqlJoin;

    public Path(String path, String field, boolean rootPath) {
        this.path = path;
        this.field = field;
        this.rootPath = rootPath;
        this.sqlJoin = SqlJoin.JOIN;
    }
}

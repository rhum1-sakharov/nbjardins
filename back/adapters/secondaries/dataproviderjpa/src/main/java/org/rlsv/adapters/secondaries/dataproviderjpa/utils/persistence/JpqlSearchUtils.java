package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import org.apache.commons.collections4.CollectionUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JpqlSearchUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JpqlSearchUtils.class);
    private static final String SPACE = " ";
    private static final String LIKE_OPERATOR = "%";

    public static String buildFilters(List<FilterAlias> filterAliasList) {
        StringBuilder sb = new StringBuilder();

        if (CollectionUtils.isNotEmpty(filterAliasList)) {
            int i = 0;
            for (FilterAlias filterAlias : filterAliasList) {

                switch (filterAlias.getOperator()) {
                    case CONTAINS:
                        sb.append(contains(filterAlias));
                        break;
                    default:
                        break;
                }

                if (filterAliasList.size() > 1 && i < (filterAliasList.size() - 1)) {
                    sb.append(SPACE)
                            .append("AND")
                            .append(SPACE);
                }

                i++;
            }
        }

        return sb.toString();
    }

    public static String contains(FilterAlias filterAlias) {
        StringBuilder sb = new StringBuilder();

        String value = filterAlias.getValue().replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(filterAlias.getAlias())
                .append(SPACE)
                .append("LIKE")
                .append(SPACE)
                .append("'" + LIKE_OPERATOR + value + LIKE_OPERATOR + "'")
                .append(SPACE);

        return sb.toString();
    }

}

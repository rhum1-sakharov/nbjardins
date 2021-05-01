package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import models.search.filter.Filter;
import models.search.filter.FilterDate;
import models.search.filter.FilterNumber;
import models.search.filter.FilterString;
import org.apache.commons.collections4.CollectionUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.SqlOperator;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class JpqlSearchUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JpqlSearchUtils.class);

    private static final String SPACE = " ";
    private static final String AND = "AND";
    private static final String COMMA = ",";
    private static final String LIKE_OPERATOR = "%";


    public static String buildFilters(List<FilterAlias> filterAliasList) {
        StringBuilder sb = new StringBuilder();

        if (CollectionUtils.isNotEmpty(filterAliasList)) {
            int i = 0;
            for (FilterAlias filterAlias : filterAliasList) {

                String alias = filterAlias.getAlias();
                Filter filter = filterAlias.getFilter();

                switch (filter.getType()) {
                    case STRING:
                        sb.append(stringBuilder(alias, filter));
                        break;
                    case NUMBER:
                        sb.append(numberBuilder(alias, filter));
                        break;
                    case DATE:
                        sb.append(dateBuilder(alias, filter));
                        break;
                    default:
                        break;
                }


                if (filterAliasList.size() > 1 && i < (filterAliasList.size() - 1)) {
                    sb.append(SPACE)
                            .append(AND)
                            .append(SPACE);
                }

                i++;
            }
        }

        return sb.toString();
    }

    private static String stringBuilder(String alias, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterString fs = (FilterString) filter;
        String[] inputs = fs.getValue();
        String input = inputs[0];

        switch (fs.getOperator()) {
            case CONTAINS:
                sb.append(stringContains(alias, input));
                break;
            case STARTS_WITH:
                sb.append(stringStartsWith(alias, input));
                break;
            case EQUALS:
                sb.append(stringEquals(alias, input));
                break;
            case NOT_EQUALS:
                sb.append(stringNotEquals(alias, input));
                break;
            case IN:
                sb.append(stringIn(alias, inputs));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String dateBuilder(String alias, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterDate fd = (FilterDate) filter;
        LocalDate[] inputs = fd.getValue();
        LocalDate input = inputs[0];

        switch (fd.getOperator()) {
            case EQUALS:
                sb.append(dateEquals(alias, input));
                break;
            case BETWEEN_INCLUSIVE:
                sb.append(dateBetweenInclusive(alias, inputs));
                break;
            case BETWEEN_EXCLUSIVE:
                sb.append(dateBetweenExclusive(alias, inputs));
                break;
            case GT:
                sb.append(dateGreaterThan(alias, input));
                break;
            case GTE:
                sb.append(dateGreaterThanOrEquals(alias, input));
                break;
            case LT:
                sb.append(dateLessThan(alias, input));
                break;
            case LTE:
                sb.append(dateLessThanOrEquals(alias, input));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String numberBuilder(String alias, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterNumber fn = (FilterNumber) filter;
        float[] inputs = fn.getValue();
        float input = inputs[0];

        switch (fn.getOperator()) {
            case IN:
                sb.append(numberIn(alias, inputs));
                break;
            case BETWEEN_EXCLUSIVE:
                sb.append(numberBetweenExclusive(alias, inputs));
                break;
            case BETWEEN_INCLUSIVE:
                sb.append(numberBetweenInclusive(alias, inputs));
                break;
            case EQUALS:
                sb.append(numberEquals(alias, input));
                break;
            case NOT_EQUALS:
                sb.append(numberNotEquals(alias, input));
                break;
            case GT:
                sb.append(numberGreaterThan(alias, input));
                break;
            case GTE:
                sb.append(numberGreaterThanOrEquals(alias, input));
                break;
            case LT:
                sb.append(numberLessThan(alias, input));
                break;
            case LTE:
                sb.append(numberLessThanOrEquals(alias, input));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String numberBetweenExclusive(String alias, float[] inputs) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append(inputs[0])
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append(inputs[1])
                .append(SPACE);

        return sb.toString();
    }


    private static String numberBetweenInclusive(String alias, float[] inputs) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append(inputs[0])
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append(inputs[1])
                .append(SPACE);

        return sb.toString();
    }

    private static String numberIn(String alias, float[] inputs) {
        StringBuilder sb = new StringBuilder();

        StringBuilder sbNumbers = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            sbNumbers.append(inputs[i]);

            if (inputs.length > 1 && i < (inputs.length - 1)) {
                sbNumbers.append(COMMA)
                        .append(SPACE);
            }

        }

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.IN.getValue())
                .append(SPACE)
                .append("(" + sbNumbers.toString() + ")")
                .append(SPACE);

        return sb.toString();
    }

    private static String numberNotEquals(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberEquals(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberGreaterThan(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberGreaterThanOrEquals(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberLessThan(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberLessThanOrEquals(String alias, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String stringContains(String alias, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LIKE.getValue())
                .append(SPACE)
                .append("'" + LIKE_OPERATOR + value + LIKE_OPERATOR + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateEquals(String alias, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateGreaterThan(String alias, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateGreaterThanOrEquals(String alias, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateLessThanOrEquals(String alias, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateLessThan(String alias, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }


    private static String stringStartsWith(String alias, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LIKE.getValue())
                .append(SPACE)
                .append("'" + value + LIKE_OPERATOR + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringEquals(String alias, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append("'" + value + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringNotEquals(String alias, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append("'" + value + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringIn(String alias, String[] inputs) {
        StringBuilder sb = new StringBuilder();

        StringBuilder sbStrings = new StringBuilder();
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            input = input.replace("'", "''");

            sbStrings.append("'" + input + "'");

            if (inputs.length > 1 && i < (inputs.length - 1)) {
                sbStrings.append(COMMA)
                        .append(SPACE);
            }
        }

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.IN.getValue())
                .append(SPACE)
                .append("(" + sbStrings.toString() + ")")
                .append(SPACE);


        return sb.toString();
    }


    private static String dateBetweenInclusive(String alias, LocalDate[] localDates) {

        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append("'" + localDates[0].toString() + "'")
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append("'" + localDates[1].toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateBetweenExclusive(String alias, LocalDate[] localDates) {

        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append("'" + localDates[0].toString() + "'")
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(alias)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append("'" + localDates[1].toString() + "'")
                .append(SPACE);

        return sb.toString();
    }
}

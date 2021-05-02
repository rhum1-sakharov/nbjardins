package org.rlsv.adapters.secondaries.dataproviderjpa.utils.persistence;

import enums.search.sort.DIRECTION;
import models.search.filter.*;
import org.apache.commons.collections4.CollectionUtils;
import org.rlsv.adapters.secondaries.dataproviderjpa.enums.SqlOperator;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.FilterPath;
import org.rlsv.adapters.secondaries.dataproviderjpa.models.search.filters.SortPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class JpqlSearchUtils {

    private static final Logger LOG = LoggerFactory.getLogger(JpqlSearchUtils.class);

    private static final String SPACE = " ";
    private static final String AND = "AND";
    private static final String COMMA = ",";
    private static final String ORDER_BY = "ORDER BY";
    private static final String LIKE_OPERATOR = "%";


    public static String buildPaths(List<FilterPath> filterPathList) {
        StringBuilder sb = new StringBuilder();

        return sb.toString();
    }

    public static String buildSorts(List<SortPath> sortPathList) {

        StringBuilder sb = new StringBuilder();


        int i = 0;
        for (SortPath sortPath : sortPathList) {

            if (i == 0) {
                sb.append(SPACE)
                        .append(ORDER_BY)
                        .append(SPACE);
            }

            sb.append(sortPath.getPath())
                    .append(SPACE)
                    .append(getDirection(sortPath.getSort().getDirection()))
                    .append(SPACE)
            ;

            if (sortPathList.size() > 1 && i < (sortPathList.size() - 1)) {
                sb.append(COMMA)
                        .append(SPACE);
            }

            i++;
        }

        return sb.toString();
    }

    public static String buildFilters(List<FilterPath> filterPathList) {
        StringBuilder sb = new StringBuilder();

        if (CollectionUtils.isNotEmpty(filterPathList)) {
            int i = 0;
            for (FilterPath filterPath : filterPathList) {

                String path = filterPath.getPath().getJoin() + "." + filterPath.getPath().getAlias();
                Filter filter = filterPath.getFilter();

                switch (filter.getType()) {
                    case STRING:
                        sb.append(stringBuilder(path, filter));
                        break;
                    case NUMBER:
                        sb.append(numberBuilder(path, filter));
                        break;
                    case DATE:
                        sb.append(dateBuilder(path, filter));
                        break;
                    case BOOLEAN:
                        sb.append(booleanBuilder(path, filter));
                        break;
                    default:
                        break;
                }


                if (filterPathList.size() > 1 && i < (filterPathList.size() - 1)) {
                    sb.append(SPACE)
                            .append(AND)
                            .append(SPACE);
                }

                i++;
            }
        }

        return sb.toString();
    }

    private static String getDirection(DIRECTION direction) {

        switch (direction) {
            case ASC:
                return "asc";
            case DESC:
                return "desc";
            default:
                return "asc";
        }

    }


    private static String stringBuilder(String path, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterString fs = (FilterString) filter;
        String[] inputs = fs.getValue();
        String input = inputs[0];

        switch (fs.getOperator()) {
            case CONTAINS:
                sb.append(stringContains(path, input));
                break;
            case STARTS_WITH:
                sb.append(stringStartsWith(path, input));
                break;
            case EQUALS:
                sb.append(stringEquals(path, input));
                break;
            case NOT_EQUALS:
                sb.append(stringNotEquals(path, input));
                break;
            case IN:
                sb.append(stringIn(path, inputs));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String booleanBuilder(String path, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterBoolean fb = (FilterBoolean) filter;
        Boolean input = fb.getValue();

        switch (fb.getOperator()) {
            case EQUALS:
                sb.append(booleanEquals(path, input));
                break;
            case NOT_EQUALS:
                sb.append(booleanNotEquals(path, input));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String dateBuilder(String path, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterDate fd = (FilterDate) filter;
        LocalDate[] inputs = fd.getValue();
        LocalDate input = inputs[0];

        switch (fd.getOperator()) {
            case EQUALS:
                sb.append(dateEquals(path, input));
                break;
            case NOT_EQUALS:
                sb.append(dateNotEquals(path, input));
                break;
            case BETWEEN_INCLUSIVE:
                sb.append(dateBetweenInclusive(path, inputs));
                break;
            case BETWEEN_EXCLUSIVE:
                sb.append(dateBetweenExclusive(path, inputs));
                break;
            case GT:
                sb.append(dateGreaterThan(path, input));
                break;
            case GTE:
                sb.append(dateGreaterThanOrEquals(path, input));
                break;
            case LT:
                sb.append(dateLessThan(path, input));
                break;
            case LTE:
                sb.append(dateLessThanOrEquals(path, input));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String numberBuilder(String path, Filter filter) {

        StringBuilder sb = new StringBuilder();

        FilterNumber fn = (FilterNumber) filter;
        float[] inputs = fn.getValue();
        float input = inputs[0];

        switch (fn.getOperator()) {
            case IN:
                sb.append(numberIn(path, inputs));
                break;
            case BETWEEN_EXCLUSIVE:
                sb.append(numberBetweenExclusive(path, inputs));
                break;
            case BETWEEN_INCLUSIVE:
                sb.append(numberBetweenInclusive(path, inputs));
                break;
            case EQUALS:
                sb.append(numberEquals(path, input));
                break;
            case NOT_EQUALS:
                sb.append(numberNotEquals(path, input));
                break;
            case GT:
                sb.append(numberGreaterThan(path, input));
                break;
            case GTE:
                sb.append(numberGreaterThanOrEquals(path, input));
                break;
            case LT:
                sb.append(numberLessThan(path, input));
                break;
            case LTE:
                sb.append(numberLessThanOrEquals(path, input));
                break;
            default:
                break;
        }

        return sb.toString();

    }

    private static String numberBetweenExclusive(String path, float[] inputs) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append(inputs[0])
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append(inputs[1])
                .append(SPACE);

        return sb.toString();
    }

    private static String numberBetweenInclusive(String path, float[] inputs) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append(inputs[0])
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append(inputs[1])
                .append(SPACE);

        return sb.toString();
    }

    private static String numberIn(String path, float[] inputs) {
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
                .append(path)
                .append(SPACE)
                .append(SqlOperator.IN.getValue())
                .append(SPACE)
                .append("(" + sbNumbers.toString() + ")")
                .append(SPACE);

        return sb.toString();
    }

    private static String numberNotEquals(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberEquals(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberGreaterThan(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberGreaterThanOrEquals(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberLessThan(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String numberLessThanOrEquals(String path, float input) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append(input)
                .append(SPACE);

        return sb.toString();
    }

    private static String stringContains(String path, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LIKE.getValue())
                .append(SPACE)
                .append("'" + LIKE_OPERATOR + value + LIKE_OPERATOR + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateEquals(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String booleanEquals(String path, Boolean value) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append(value)
                .append(SPACE);

        return sb.toString();
    }

    private static String booleanNotEquals(String path, Boolean value) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append(value)
                .append(SPACE);

        return sb.toString();
    }

    private static String dateNotEquals(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateGreaterThan(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateGreaterThanOrEquals(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateLessThanOrEquals(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateLessThan(String path, LocalDate localDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append("'" + localDate.toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringStartsWith(String path, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LIKE.getValue())
                .append(SPACE)
                .append("'" + value + LIKE_OPERATOR + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringEquals(String path, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.EQUALS.getValue())
                .append(SPACE)
                .append("'" + value + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringNotEquals(String path, String input) {
        StringBuilder sb = new StringBuilder();

        String value = input.replace("'", "''");
        value = value.replace(SPACE, LIKE_OPERATOR);

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.NOT_EQUALS.getValue())
                .append(SPACE)
                .append("'" + value + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String stringIn(String path, String[] inputs) {
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
                .append(path)
                .append(SPACE)
                .append(SqlOperator.IN.getValue())
                .append(SPACE)
                .append("(" + sbStrings.toString() + ")")
                .append(SPACE);


        return sb.toString();
    }

    private static String dateBetweenInclusive(String path, LocalDate[] localDates) {

        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GTE.getValue())
                .append(SPACE)
                .append("'" + localDates[0].toString() + "'")
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LTE.getValue())
                .append(SPACE)
                .append("'" + localDates[1].toString() + "'")
                .append(SPACE);

        return sb.toString();
    }

    private static String dateBetweenExclusive(String path, LocalDate[] localDates) {

        StringBuilder sb = new StringBuilder();

        sb.append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.GT.getValue())
                .append(SPACE)
                .append("'" + localDates[0].toString() + "'")
                .append(SPACE)
                .append(AND)
                .append(SPACE)
                .append(path)
                .append(SPACE)
                .append(SqlOperator.LT.getValue())
                .append(SPACE)
                .append("'" + localDates[1].toString() + "'")
                .append(SPACE);

        return sb.toString();
    }
}

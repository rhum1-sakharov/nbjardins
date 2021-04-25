package helpers.search.filters;

import exceptions.TechnicalException;
import keys.DomainKey;
import models.search.Search;
import models.search.filter.Filter;
import models.search.sort.Sort;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import ports.localization.LocalizeServicePT;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static localizations.MessageKeys.*;

public class SearchFilterHelper<DK extends DomainKey> {

    LocalizeServicePT ls;

    public SearchFilterHelper(LocalizeServicePT ls) {
        this.ls = ls;
    }

    public void checkSearch(Search search, Class<DK> kfClass) throws TechnicalException {
        checkFilters(search.getFilters(), kfClass);
        checkSorts(search.getSorts(), kfClass);
    }

    public void checkFilters(List<Filter> filters, Class<DK> kfClass) throws TechnicalException {

        if (CollectionUtils.isNotEmpty(filters)) {
            List<String> keyNames = filters.stream().map(item -> item.getKey()).collect(Collectors.toList());
            checkKeys(keyNames, kfClass, FILTER_KEY_IS_UNKNOWN);


            checkFilterValues(filters, kfClass);
        }

    }

    public void checkSorts(List<Sort> sorts, Class<DK> kfClass) throws TechnicalException {
        if (CollectionUtils.isNotEmpty(sorts)) {
            List<String> keyNames = sorts.stream().map(item -> item.getKey()).collect(Collectors.toList());
            checkKeys(keyNames, kfClass, SORT_KEY_IS_UNKNOWN);
        }
    }

    private void checkFilterValues(List<Filter> filters, Class<DK> kfClass) throws TechnicalException {
        Field[] fields = kfClass.getFields();
        StringBuilder err = new StringBuilder();


         if (ArrayUtils.isNotEmpty(fields)
                && CollectionUtils.isNotEmpty(filters)) {

            for (Filter filter : filters) {

                switch (filter.getOperator()) {
                    case ID_IN:
                        if(Objects.isNull(filter.getIdList())){
                            err.append(ls.getMsg(FILTER_VALUE_IS_NOT_A_LIST, kfClass.getSimpleName(), filter.getKey())).append(System.lineSeparator());
                        }
                        break;
                    default:
                        if(Objects.isNull(filter.getValue())){
                            err.append(ls.getMsg(FILTER_VALUE_IS_NULL, kfClass.getSimpleName(), filter.getKey())).append(System.lineSeparator());
                        }
                        break;
                }
            }
        }

        if (StringUtils.isNotEmpty(err.toString())) {
            throw new TechnicalException(err.toString());
        }
    }

    private void checkKeys(List<String> keyNames, Class<DK> kfClass, final String localeKeyMsg) throws TechnicalException {
        Field[] fields = kfClass.getFields();
        StringBuilder err = new StringBuilder();


        if (ArrayUtils.isNotEmpty(fields)
                && CollectionUtils.isNotEmpty(keyNames)) {

            for (String keyName : keyNames) {

                boolean found = false;
                for (Field field : fields) {
                    if (keyName.equals(field.getName())) {
                        found = true;
                    }
                }
                if (!found) {
                    err.append(ls.getMsg(localeKeyMsg, keyName, kfClass.getSimpleName())).append(System.lineSeparator());
                }
            }
        }

        if (StringUtils.isNotEmpty(err.toString())) {
            throw new TechnicalException(err.toString());
        }
    }


}

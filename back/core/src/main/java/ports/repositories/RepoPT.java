package ports.repositories;

import domains.Domain;
import exceptions.TechnicalException;
import transactions.DataProviderManager;

import java.util.List;

public interface RepoPT<D extends Domain> {

    List<String> deleteByIdList(DataProviderManager dpm, Class<D> clazz, List<String> idList) throws TechnicalException;

    String deleteById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException;

    D save(DataProviderManager dpm, D domain) throws TechnicalException;

}

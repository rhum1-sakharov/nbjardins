package ports.repositories;

import domains.Domain;
import exceptions.TechnicalException;
import models.search.Search;
import models.search.response.SearchResponse;
import transactions.DataProviderManager;

import java.util.List;

public interface RepoPT<D extends Domain> {

    List<String> deleteByIdList(DataProviderManager dpm, Class<D> clazz, List<String> idList) throws TechnicalException;

    String deleteById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException;

    D save(DataProviderManager dpm, D domain) throws TechnicalException;

    D findById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException;

    List<D> findAll(DataProviderManager dpm, Class<D> domainClass) throws TechnicalException;

     SearchResponse<D> search(DataProviderManager dpm, Search search, Class<D> domainClass) throws TechnicalException, InstantiationException, IllegalAccessException;


}

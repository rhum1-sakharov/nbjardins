package repository.personne;

import config.DomainJdbcMapping;
import domains.Domain;
import exceptions.TechnicalException;
import models.search.Search;
import models.search.response.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.RepoPT;
import transactions.DataProviderManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepoJdbc<D extends Domain> implements RepoPT<D> {

    private static final Logger LOG = LoggerFactory.getLogger(RepoJdbc.class);

    protected LocalizeServicePT ls;

    public RepoJdbc(LocalizeServicePT ls) {
        this.ls = ls;
    }


    @Override
    public List<String> deleteByIdList(DataProviderManager dpm, Class<D> clazz, List<String> idList) throws TechnicalException {
        return null;
    }

    @Override
    public String deleteById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException {
        return null;
    }

    @Override
    public D save(DataProviderManager dpm, D domain) throws TechnicalException {
        return null;
    }

    @Override
    public D findById(DataProviderManager dpm, Class<D> domainClass, String id) throws TechnicalException {
        return null;
    }

    @Override
    public List<D> findAll(DataProviderManager dpm, Class<D> domainClass) throws TechnicalException {

        Connection connection = (Connection) dpm.getManager();
        final String sqlQuery = "select * from " + DomainJdbcMapping.getTable(domainClass.getSimpleName());

        List<D> domainList = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(sqlQuery);
             ResultSet rs = pst.executeQuery();
        ) {
            while(rs.next()){
                try {
                    D instance = domainClass.newInstance();


                } catch (InstantiationException | IllegalAccessException e) {
                    LOG.error(e.getMessage());
                }
            }

        } catch (SQLException e) {
            throw new TechnicalException(e.getMessage(), e, "");
        }

        return domainList;
    }

    @Override
    public SearchResponse<D> search(DataProviderManager dpm, Search search, Class<D> domainClass) throws TechnicalException, InstantiationException, IllegalAccessException {
        return null;
    }
}

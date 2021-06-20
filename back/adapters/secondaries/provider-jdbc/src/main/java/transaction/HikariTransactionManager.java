package transaction;

import config.DataSource;
import exceptions.CleanException;
import exceptions.TechnicalException;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class HikariTransactionManager implements TransactionManagerPT {

    @Override
    public DataProviderManager createDataProviderManager(DataProviderManager dpm) throws CleanException {

        if (Objects.isNull(dpm)) {

            dpm = new DataProviderManager();
            Connection connection = DataSource.getConnection();
            dpm.setManager(connection);

        }

        return dpm;
    }

    @Override
    public void begin(DataProviderManager dpm) throws CleanException {

        if (Objects.isNull(dpm)) {

        }

        try {
            Connection connection = (Connection) dpm.getManager();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TechnicalException(e.getMessage(), e, "");
        }
    }

    @Override
    public void commit(DataProviderManager dpm) throws CleanException {

        if (Objects.isNull(dpm)) {

        }

        try {
            Connection connection = (Connection) dpm.getManager();
            connection.commit();
        } catch (SQLException e) {
            throw new TechnicalException(e.getMessage(), e, "");
        }

    }

    @Override
    public void rollback(DataProviderManager dpm) {

    }

    @Override
    public void close(DataProviderManager dpm) {

    }
}

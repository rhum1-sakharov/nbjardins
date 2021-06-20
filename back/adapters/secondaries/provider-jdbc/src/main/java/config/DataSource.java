package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.SqlConnectionException;
import model.HikariConfigML;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ThreadUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


public class DataSource {

    private static final Logger LOG = LoggerFactory.getLogger(DataSource.class);
    private static HikariDataSource ds;
    private static volatile DataSource INSTANCE;
    private static Object mutex = new Object();

    public static HikariConfigML HIKARI_CONFIG_ML;

    private DataSource(HikariConfigML configML) {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(configML.getUrl());
        config.setUsername(configML.getUser());
        config.setPassword(configML.getPassword());
        config.setDriverClassName(configML.getDriver());

        if(StringUtils.isNotEmpty(configML.getPoolName())){
            config.setPoolName(configML.getPoolName());
        }


        Integer maxPool = configML.getMaximumPoolSize();
        if (Objects.isNull(maxPool) || maxPool < 2) {
            maxPool = ThreadUtil.maxDbPoolConnections(1);
        }
        config.setMaximumPoolSize(maxPool);

        ds = new HikariDataSource(config);

        LOG.info("jdbc url             : {}",config.getJdbcUrl());
        LOG.info("username             : {}",config.getUsername());
        LOG.info("driverClassName      : {}",config.getDriverClassName());
        LOG.info("pool name            : {}",config.getPoolName());
        LOG.info("max pool connections : {}",config.getMaximumPoolSize());

    }

    public static DataSource getInstance() {

        DataSource result = INSTANCE;
        if (Objects.isNull(result)) {
            synchronized (mutex) {
                result = INSTANCE;
                if (Objects.isNull(result)) {
                    INSTANCE = result = new DataSource(HIKARI_CONFIG_ML);
                }
            }
        }

        return result;
    }




    public static Connection getConnection() throws SqlConnectionException {

        if (Objects.isNull(ds)) {
            DataSource.getInstance();
        }

        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new SqlConnectionException(e);
        }
    }

}

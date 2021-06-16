package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.SqlConnectionException;

import java.sql.Connection;
import java.sql.SQLException;


public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;



    private DataSource() {
    }

    public static Connection getConnection() throws SqlConnectionException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new SqlConnectionException(e);
        }
    }

}

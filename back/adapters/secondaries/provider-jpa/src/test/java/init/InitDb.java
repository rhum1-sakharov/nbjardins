package init;

import org.rlsv.adapters.secondaries.dataproviderjpa.config.DatabaseConnectionConfig;
import org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig;

import static org.rlsv.adapters.secondaries.dataproviderjpa.config.JtaConfig.PERSISTENCE_UNIT_RLSV;

public class InitDb {

    public static void init(){
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/unit_tests_nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";

        JtaConfig.databaseConnectionConfig = new DatabaseConnectionConfig(user, password, url, driver, PERSISTENCE_UNIT_RLSV);
    }

}

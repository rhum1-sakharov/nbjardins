package util;

import config.DataSource;
import model.HikariConfigML;

public class TestUtil {

    public static void initHikariConfigML() {

        DataSource.HIKARI_CONFIG_ML = new HikariConfigML().builder()
                .driver("com.mysql.cj.jdbc.Driver")
                .password("")
                .poolName("HIKARI-TEST-POOL")
                .url("jdbc:mysql://localhost:3306/nbjardins?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC")
                .user("root")
                .build();
    }

}

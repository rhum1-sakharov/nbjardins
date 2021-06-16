package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HikariConfigML {


    private String user;
    private String password;
    private String url;
    private String driver;
    private int maximumPoolSize;


}

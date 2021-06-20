package model;

import lombok.*;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HikariConfigML {


    private String user;
    private String password;
    private String url;
    private String driver;
    private Integer maximumPoolSize;
    private String poolName;


}

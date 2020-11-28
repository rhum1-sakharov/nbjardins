package domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerMail extends Entity {

    String host;
    boolean isAuth;
    int port;
    String userAccount;
    String passwordAccount;
    boolean enableTls;
    boolean requiredTls;

}

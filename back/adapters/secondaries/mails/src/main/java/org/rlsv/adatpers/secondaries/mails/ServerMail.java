package org.rlsv.adatpers.secondaries.mails;

import domains.models.Domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerMail extends Domain {

    String host;
    boolean isAuth;
    int port;
    String userAccount;
    String passwordAccount;
    boolean enableTls;
    boolean requiredTls;
    String resourceUrl;

}

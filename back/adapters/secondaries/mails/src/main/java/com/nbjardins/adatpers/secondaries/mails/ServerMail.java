package com.nbjardins.adatpers.secondaries.mails;

import domain.models.EntityDN;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerMail extends EntityDN {

    String host;
    boolean isAuth;
    int port;
    String userAccount;
    String passwordAccount;
    boolean enableTls;
    boolean requiredTls;
    String resourceUrl;

}

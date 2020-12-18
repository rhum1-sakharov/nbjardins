package org.rlsv.adatpers.secondaries.mails;

import ports.localization.LocalizeServicePT;

public class AbstractMail {

    protected ServerMail serverMail;
    protected LocalizeServicePT localize;


    public AbstractMail(ServerMail serverMail, LocalizeServicePT localize) {
        this.serverMail = serverMail;
        this.localize = localize;
    }


}

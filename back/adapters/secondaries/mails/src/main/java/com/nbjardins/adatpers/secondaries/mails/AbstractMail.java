package com.nbjardins.adatpers.secondaries.mails;

import usecase.ports.LocalizeServicePT;

public class AbstractMail {

    protected ServerMail serverMail;
    protected LocalizeServicePT localize;


    public AbstractMail(ServerMail serverMail, LocalizeServicePT localize) {
        this.serverMail = serverMail;
        this.localize = localize;
    }


}

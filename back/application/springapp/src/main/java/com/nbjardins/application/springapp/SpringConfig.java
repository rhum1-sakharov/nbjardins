package com.nbjardins.application.springapp;

import com.nbjardins.adatpers.secondaries.mails.AJavaMail;
import usecase.RealiserDevis;
import usecase.ports.PMailService;

public class SpringConfig {

    private final PMailService pMailService = new AJavaMail();

    public RealiserDevis realiserDevis() {
        return new RealiserDevis(pMailService);
    }

}

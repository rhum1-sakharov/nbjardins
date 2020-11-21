package com.nbjardins.config;

import com.nbjardins.adatper.mailservice.AJavaMail;
import com.nbjardins.usecase.RealiserDevis;
import com.nbjardins.usecase.ports.PMailService;

public class SpringConfig {

    private final PMailService pMailService = new AJavaMail();

    public RealiserDevis realiserDevis() {
        return new RealiserDevis(pMailService);
    }

}

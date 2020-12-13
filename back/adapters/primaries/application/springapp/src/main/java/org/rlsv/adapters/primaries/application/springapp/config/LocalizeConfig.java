package org.rlsv.adapters.primaries.application.springapp.config;

import org.rlsv.adapters.secondaries.localization.LocalizeResourceBundleAR;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usecase.ports.localization.LocalizeServicePT;

@Configuration
public class LocalizeConfig {



    @Bean
    public LocalizeServicePT localizeServicePT() {
        return new LocalizeResourceBundleAR();
    }





}

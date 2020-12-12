package org.rlsv.adatpers.secondaries.mails.springmail;

import lombok.Getter;

public enum TemplateLocation {

    ACKNOWLEDGEMENT_DEMANDE_DEVIS_TO_SENDER("acknowledgement_demande_devis_to_sender.html"),
    DEMANDE_DEVIS_TO_WORKER("demande_devis_to_worker.html");

    @Getter
    private String htmlLocation;

    TemplateLocation(String htmlLocation){
        this.htmlLocation = htmlLocation;
    }

}

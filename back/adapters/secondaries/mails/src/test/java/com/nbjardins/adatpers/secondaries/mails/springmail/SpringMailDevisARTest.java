package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.entities.DemandeDeDevis;
import domain.entityresponse.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class SpringMailDevisARTest {

    DemandeDeDevis demandeDeDevis;
    SpringMailDevisAR springMailDevisAR;
    GreenMail smtpServer;

    @BeforeEach
    public void setUp() {

        ServerMail serverMail = new ServerMail("localhost", false, 25, null, null, false, false);
        smtpServer = new GreenMail(new ServerSetup(serverMail.getPort(), null, "smtp"));
        smtpServer.start();

        demandeDeDevis = new DemandeDeDevis("nomEmetteur", "prenomEmetteur", "0699457899", "blelezfz0", "", "", "360 route de londres", null, "[Demande de devis]", "from@test.fr", "to@test.fr", "nbjardins");

        springMailDevisAR = new SpringMailDevisAR(serverMail, null);
    }

    @Test
    void should_send_to_worker() {
        Response<DemandeDeDevis> demandeDeDevisResponse = springMailDevisAR.sendToWorker(demandeDeDevis);
        Assertions.assertThat(Objects.nonNull(demandeDeDevisResponse)).isTrue();
        Assertions.assertThat(demandeDeDevisResponse.isError()).isFalse();
    }

    @AfterEach
    public void tearDown() {
        smtpServer.stop();
    }
}
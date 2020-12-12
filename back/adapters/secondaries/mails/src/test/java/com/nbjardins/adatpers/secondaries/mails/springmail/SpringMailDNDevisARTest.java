package com.nbjardins.adatpers.secondaries.mails.springmail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.nbjardins.adatpers.secondaries.mails.ServerMail;
import domain.models.DemandeDeDevisDN;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpringMailDNDevisARTest {

    DemandeDeDevisDN demandeDeDevisDN;
    SpringMailDevisAR springMailDevisAR;
    GreenMail smtpServer;

    @BeforeEach
    public void setUp() {

        ServerMail serverMail = new ServerMail("localhost", false, 25, null, null, false, false,"");
        smtpServer = new GreenMail(new ServerSetup(serverMail.getPort(), null, "smtp"));
        smtpServer.start();

//        demandeDeDevisDN = new DemandeDeDevisDN("nomEmetteur", "prenomEmetteur", "0699457899", "blelezfz0", "", "", "360 route de londres", null, "[Demande de devis]", "from@test.fr", "to@test.fr", "nbjardins",null,null,null,null,null);

//        springMailDevisAR = new SpringMailDevisAR(serverMail, null);
    }

    @Test
    void should_send_to_worker() {
//        Response<DemandeDeDevisDN> demandeDeDevisResponse = springMailDevisAR.sendToWorker(demandeDeDevisDN);
//        Assertions.assertThat(Objects.nonNull(demandeDeDevisResponse)).isTrue();
//        Assertions.assertThat(demandeDeDevisResponse.isError()).isFalse();
    }

    @AfterEach
    public void tearDown() {
        smtpServer.stop();
    }
}
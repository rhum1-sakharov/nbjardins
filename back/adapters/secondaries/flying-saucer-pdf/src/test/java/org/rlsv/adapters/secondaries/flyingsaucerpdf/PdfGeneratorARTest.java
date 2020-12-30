package org.rlsv.adapters.secondaries.flyingsaucerpdf;

import domain.models.DevisDN;
import domain.models.PersonneDN;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class PdfGeneratorARTest {

    PdfGeneratorAR pdfGeneratorAR;


    @Before
    public void setUp() throws Exception {
        this.pdfGeneratorAR = new PdfGeneratorAR();
    }

    @Test
    public void genererDevisPDF() {
        ByteArrayOutputStream baos = pdfGeneratorAR.genererDevisPDF(initDevis());

        try (OutputStream os = new FileOutputStream("test-devis.pdf")) {
            baos.writeTo(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertThat(Objects.nonNull(baos)).isTrue();


    }

    private DevisDN initDevis() {

        PersonneDN worker = new PersonneDN();
        worker.setNom("Boetsch");
        worker.setSociete("Les jardins de Nicolas");
        worker.setAdresse("366 chemin des castanes");
        worker.setCodePostal("84210");
        worker.setVille("Pernes les Fontaines");
        worker.setNumeroTelephone("0612345678");
        worker.setEmail("nb@laposte.net");
        // TODO worker setSite
        // worker.setSite("www.nb.com");

        PersonneDN asker = new PersonneDN();
        asker.setNom("Tartenpion");
        asker.setPrenom("Milou");
        asker.setAdresse("544 chemin de Fontblanque");
        asker.setVille("Mazan");
        asker.setCodePostal("84350");

        DevisDN devis = new DevisDN();

        devis.setWorker(worker);
        devis.setAsker(asker);

        devis.setNumeroDevis("20201230-001-ABN");
        devis.setLieu(worker.getVille());
        devis.setDateEnCours(new Date());
        devis.setSujet("Elagage des arbres de la propriété du 544 chemin de Fontblanque à Mazan");

        // TODO lignes devis
        devis.setConditionDeReglement("à réception de la facture");

        // TODO reglement à l'ordre de
        devis.setRib("CL MONTPELLIER (03000) 30002 11111 1111171110U 03");
        devis.setIban("IBAN FR63 XXXX XXXX XXXX XXXX XXXX U03");

        devis.setProvision(new BigDecimal(30));

        devis.setLogo("logo_nb_jardins.png");
        devis.setSignature("signature_nb_jardins.png");


        return devis;
    }
}
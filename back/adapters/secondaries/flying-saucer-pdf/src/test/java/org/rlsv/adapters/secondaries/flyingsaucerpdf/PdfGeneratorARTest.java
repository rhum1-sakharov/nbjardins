package org.rlsv.adapters.secondaries.flyingsaucerpdf;

import domain.models.DevisDN;
import domain.models.PersonneDN;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

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


    }

    private DevisDN initDevis() {

        PersonneDN worker = new PersonneDN();
        worker.setNom("Boetsch");


        DevisDN devis = new DevisDN();

        devis.setWorker(worker);

        return devis;
    }
}
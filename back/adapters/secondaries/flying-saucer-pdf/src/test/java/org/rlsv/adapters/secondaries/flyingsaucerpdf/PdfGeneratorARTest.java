package org.rlsv.adapters.secondaries.flyingsaucerpdf;

import domains.applications.ApplicationDN;
import domains.devis.DevisDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class PdfGeneratorARTest {

    PdfGeneratorAR pdfGeneratorAR;


    @Before
    public void setUp() throws Exception {
        this.pdfGeneratorAR = new PdfGeneratorAR();
    }

    @Test
    public void genererDevisPDF(){
//        ByteArrayOutputStream baos = null;
//
//        try (OutputStream os = new FileOutputStream("test-devisATraiter.pdf")) {
//            baos = pdfGeneratorAR.genererDevisPDF(initDevis(true));
//            baos.writeTo(os);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (PdfException e) {
//            e.printStackTrace();
//        }
//
//        Assertions.assertThat(Objects.nonNull(baos)).isTrue();

    }

    @Test
    public void genererDevisPDF_with_no_ligne_should_return_not_null() {

//        ByteArrayOutputStream baos =null;
//
//        try (OutputStream os = new FileOutputStream("test-devisATraiter-2.pdf")) {
//            baos = pdfGeneratorAR.genererDevisPDF(initDevis(false));
//            baos.writeTo(os);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (PdfException e) {
//            e.printStackTrace();
//        }
//
//        Assertions.assertThat(Objects.nonNull(baos)).isTrue();

    }

    private DevisDN initDevis(boolean enableLignes) {

        ArtisanDN artisan = new ArtisanDN();

        PersonneDN artisanPersonne = new PersonneDN();
        artisanPersonne.setNom("Boetsch");
        artisanPersonne.setSociete("Les jardins de Nicolas");
        artisanPersonne.setAdresse("366 chemin des castanes");
        artisanPersonne.setCodePostal("84210");
        artisanPersonne.setVille("Pernes les Fontaines");
        artisanPersonne.setNumeroTelephone("0612345678");
        artisanPersonne.setEmail("nb@laposte.net");

        artisan.setPersonne(artisanPersonne);
        artisan.setSiret("80025030000011");


        ApplicationDN application = new ApplicationDN();
        application.setSite("vps358243.ovh.net:81");
        artisan.setApplication(application);

        ClientDN client = new ClientDN();
        PersonneDN asker = new PersonneDN();
        asker.setNom("Tartenpion");
        asker.setPrenom("Milou");
        asker.setAdresse("544 chemin de Fontblanque");
        asker.setVille("Mazan");
        asker.setCodePostal("84350");


        DevisDN devis = new DevisDN();

        devis.setArtisan(artisan);


        devis.setValiditeDevisMois(3);
        devis.setNumeroDevis("20201230-001-ABN");
        devis.setLieu(artisan.getPersonne().getVille());

        devis.setTva(new BigDecimal(10));
        devis.setSujet("Elagage des arbres de la propriété du 544 chemin de Fontblanque à Mazan");

//        if (enableLignes) {
//            DevisLigneDN devisLigne1 = new DevisLigneDN("Modificatif de l'etat descriptif", new BigDecimal(550.25));
//            DevisLigneDN devisLigne2 = new DevisLigneDN("Attestation de surface loi carrez", new BigDecimal(125.78));
//            List<DevisLigneDN> devisLigneDNList = new ArrayList<>();
//            devisLigneDNList.add(devisLigne1);
//            devisLigneDNList.add(devisLigne2);
////            devisATraiter.setDevisLigneList(devisLigneDNList);
//
//        }

        devis.setConditionDeReglement("à réception de la facture");

        devis.setRib("CL MONTPELLIER (03000) 30002 11111 1111171110U 03");
        devis.setIban("IBAN FR63 XXXX XXXX XXXX XXXX XXXX U03");

        devis.setProvision(new BigDecimal(30));




        return devis;
    }
}
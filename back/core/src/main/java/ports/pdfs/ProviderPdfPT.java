package ports.pdfs;

import domain.models.DevisDN;

import java.io.ByteArrayOutputStream;

public interface ProviderPdfPT {

    ByteArrayOutputStream genererDevisPDF(DevisDN devis);
}

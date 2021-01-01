package ports.pdfs;

import domains.models.DevisDN;

import java.io.ByteArrayOutputStream;

public interface ProviderPdfPT {

    ByteArrayOutputStream genererDevisPDF(DevisDN devis);
}

package ports.pdfs;

import domains.models.DevisDN;
import exceptions.PdfException;

import java.io.ByteArrayOutputStream;

public interface ProviderPdfPT {

    ByteArrayOutputStream genererDevisPDF(DevisDN devis) throws PdfException;
}

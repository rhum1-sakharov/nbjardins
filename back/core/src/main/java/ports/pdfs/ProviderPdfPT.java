package ports.pdfs;

import domains.DevisDN;
import exceptions.PdfException;

import java.io.ByteArrayOutputStream;

public interface ProviderPdfPT {

    ByteArrayOutputStream genererDevisPDF(DevisDN devis) throws PdfException;
}

package ports.pdfs;

import domains.devis.DevisDN;
import exceptions.PdfException;

import java.io.ByteArrayOutputStream;

public interface ProviderPdfPT {

    ByteArrayOutputStream genererDevisPDF(DevisDN devis) throws PdfException;
}

package usecases.devis;

import domains.devis.DevisDN;
import exceptions.CleanException;
import ports.localization.LocalizeServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.transactions.TransactionManagerPT;
import usecases.AbstractUsecase;

import java.io.ByteArrayOutputStream;

public class GenerateDevisPdfUE extends AbstractUsecase {


    private final ProviderPdfPT providerPdf;

    public GenerateDevisPdfUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ProviderPdfPT providerPdf) {
        super(localizeService, transactionManager);
        this.providerPdf = providerPdf;
    }


    public ByteArrayOutputStream execute(DevisDN devis) throws CleanException {
        return this.providerPdf.genererDevisPDF(devis);
    }
}

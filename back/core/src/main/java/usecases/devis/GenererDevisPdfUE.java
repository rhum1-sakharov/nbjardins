package usecases.devis;

import domains.models.DevisDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.transactions.TransactionManagerPT;
import usecases.AbstractUsecase;
import usecases.IUsecase;
import utils.Utils;

import java.io.ByteArrayOutputStream;

import static domains.wrapper.RequestMap.REQUEST_KEY_DEVIS;
import static localizations.MessageKeys.PARAMETRE_DEVIS_OBLIGATOIRE;

public class GenererDevisPdfUE extends AbstractUsecase implements IUsecase {


    private final ProviderPdfPT providerPdf;

    public GenererDevisPdfUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, ProviderPdfPT providerPdf) {
        super(localizeService, transactionManager);
        this.providerPdf = providerPdf;
    }


    public ResponseDN execute(RequestMap requestMap) throws Exception {

        ResponseDN responseDN = Utils.initResponse(localizeService.getMsg(PARAMETRE_DEVIS_OBLIGATOIRE), !(requestMap.get(REQUEST_KEY_DEVIS) instanceof DevisDN));

        if (responseDN.isError()) {
            return responseDN;
        }

        DevisDN devis = (DevisDN) requestMap.get(REQUEST_KEY_DEVIS);
        ByteArrayOutputStream baos = this.providerPdf.genererDevisPDF(devis);

        responseDN.addResultList(baos);

        return responseDN;
    }
}

package usecase.devis;

import domain.models.DevisDN;
import domain.utils.Utils;
import domain.wrapper.RequestMap;
import domain.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import ports.pdfs.ProviderPdfPT;
import ports.transactions.TransactionManagerPT;
import usecase.AbstractUsecase;

import java.io.ByteArrayOutputStream;

import static domain.localization.MessageKeys.PARAMETRE_DEVIS_OBLIGATOIRE;

public class GenererDevisPdfUE extends AbstractUsecase {

    public static final String REQUEST_KEY_DEVIS = "REQUEST_KEY_DEVIS";

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

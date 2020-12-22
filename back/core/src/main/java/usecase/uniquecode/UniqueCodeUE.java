package usecase.uniquecode;

import domain.enums.UNIQUE_CODE;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import usecase.AbstractUsecase;
import usecase.IUsecase;

import java.util.Date;

public class UniqueCodeUE extends AbstractUsecase implements IUsecase {

    private final String UNIQUE_CODE_KEY = "UNIQUE_CODE_KEY";
    private final DevisRepoPT devisRepo;

    public UniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Override
    public ResponseDN execute(RequestDN instance) throws Exception {

        ResponseDN response = new ResponseDN<>();

        String uniqueCode = "";

        UNIQUE_CODE unique_code = (UNIQUE_CODE) instance.getAdditionalProperties().get(UNIQUE_CODE_KEY);

        switch (unique_code) {
            case NUMERO_DEVIS:
                uniqueCode = generateNumeroDevis();
                break;
            default:
                break;
        }

        response.setOne(uniqueCode);

        return response;
    }

    public String generateNumeroDevis() {
        String numeroDevis = "";

        int countDevisOfMonth = this.devisRepo.countDevisOfMonth(new Date());

        // 202012-16511

        return numeroDevis;
    }
}

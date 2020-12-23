package usecase.uniquecode;

import domain.enums.UNIQUE_CODE;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.apache.commons.lang3.RandomStringUtils;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecase.AbstractUsecase;
import usecase.IUsecase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UniqueCodeUE extends AbstractUsecase implements IUsecase {

    public static final String UNIQUE_CODE_KEY = "UNIQUE_CODE_KEY";
    private final DevisRepoPT devisRepo;

    public UniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Override
    public ResponseDN execute(RequestDN instance) throws Exception {

        DataProviderManager dpm = transactionManager.createDataProviderManager(instance.getDataProviderManager());

        ResponseDN response = new ResponseDN<>();

        String uniqueCode = "";

        UNIQUE_CODE unique_code = (UNIQUE_CODE) instance.getAdditionalProperties().get(UNIQUE_CODE_KEY);

        switch (unique_code) {
            case NUMERO_DEVIS:
                uniqueCode = generateNumeroDevis(dpm);
                break;
            default:
                break;
        }

        response.setOne(uniqueCode);

        return response;
    }

    public String generateNumeroDevis(DataProviderManager dpm) {


        Date now = new Date();

        int countDevisOfMonth = this.devisRepo.countDevisOfMonth(dpm, now);
        SimpleDateFormat spd = new SimpleDateFormat("yyyyMM");


        String generatedString = randomString(4, true, true);

        // 202012-21165-NB7A
        String numeroDevis = spd.format(now) + "-" + String.format("%d05", countDevisOfMonth + 1) + "-" + generatedString;

        int exists = devisRepo.existsNumeroDevis(dpm, numeroDevis);

        if (exists > 0) {
            generateNumeroDevis(dpm);
        }

        return numeroDevis;
    }

    private String randomString(int length, boolean useLetters, boolean useNumbers) {

        return RandomStringUtils.random(length, useLetters, useNumbers);

    }
}

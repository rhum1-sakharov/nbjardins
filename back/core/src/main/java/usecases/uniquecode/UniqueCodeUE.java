package usecases.uniquecode;

import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import enums.UNIQUE_CODE;
import org.apache.commons.lang3.RandomStringUtils;
import ports.localization.LocalizeServicePT;
import ports.repositories.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.IUsecase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static domains.wrapper.RequestMap.REQUEST_KEY_UNIQUECODE;

public class UniqueCodeUE extends AbstractUsecase implements IUsecase {


    private final DevisRepoPT devisRepo;

    public UniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }

    @Override
    public ResponseDN execute(RequestMap requestMap) throws Exception {

        DataProviderManager dpm = transactionManager.createDataProviderManager(requestMap.getDataProviderManager());

        ResponseDN response = new ResponseDN<>();

        String uniqueCode = "";

        UNIQUE_CODE unique_code = (UNIQUE_CODE) requestMap.get(REQUEST_KEY_UNIQUECODE);

        switch (unique_code) {
            case NUMERO_DEVIS:
                uniqueCode = generateNumeroDevis(dpm);
                break;
            default:
                break;
        }

        response.addResultList(uniqueCode);

        return response;
    }

    public String generateNumeroDevis(DataProviderManager dpm) {


        Date now = new Date();

        Long countDevisOfMonth = this.devisRepo.countDevisOfMonth(dpm, now);
        SimpleDateFormat spd = new SimpleDateFormat("yyyyMM");


        String generatedString = randomString(2, true, true).toUpperCase();

        // 12 caracteres 202012-211NB
        String numeroDevis = spd.format(now) + "-" + String.format("%03d", countDevisOfMonth + 1) +  generatedString;

        Long exists = devisRepo.existsNumeroDevis(dpm, numeroDevis);

        if (exists > 0) {
            generateNumeroDevis(dpm);
        }

        return numeroDevis;
    }

    private String randomString(int length, boolean useLetters, boolean useNumbers) {

        return RandomStringUtils.random(length, useLetters, useNumbers);

    }
}

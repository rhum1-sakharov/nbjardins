package usecases.uniquecode;

import annotations.Transactional;
import enums.UNIQUE_CODE;
import exceptions.CleanException;
import org.apache.commons.lang3.RandomStringUtils;
import ports.localization.LocalizeServicePT;
import ports.repositories.devis.DevisRepoPT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetUniqueCodeUE extends AbstractUsecase {


    private final DevisRepoPT devisRepo;

    public GetUniqueCodeUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager, DevisRepoPT devisRepo) {
        super(localizeService, transactionManager);
        this.devisRepo = devisRepo;
    }


    @Transactional
    public String  execute( DataProviderManager dpm, UNIQUE_CODE unique_code) throws CleanException {

        String uniqueCode = "";

        switch (unique_code) {
            case NUMERO_DEVIS:
                uniqueCode = generateNumeroDevis(dpm);
                break;
            default:
                break;
        }

        return uniqueCode;
    }

    public String generateNumeroDevis(DataProviderManager dpm) {


        Date now = new Date();

        Long countDevisOfMonth = this.devisRepo.countDevisOfMonth(dpm, now);
        SimpleDateFormat spd = new SimpleDateFormat("yyyyMM");


        String generatedString = randomString(3, true, true).toUpperCase();

        // 12 caracteres 202012211NB0
        String numeroDevis = spd.format(now)  + String.format("%03d", countDevisOfMonth + 1) + generatedString;

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

package usecase.devis;

import domain.enums.TYPE_CREATION_DEVIS;
import domain.models.DevisDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecase.AbstractUsecase;
import usecase.IUsecase;

public class CreerDevisUE extends AbstractUsecase implements IUsecase {

    private static final String KEY_TYPE_CREATION_DEVIS = "key.type.creation.devis";

    public CreerDevisUE(LocalizeServicePT localizeService, TransactionManagerPT transactionManager) {
        super(localizeService, transactionManager);
    }

    /**
     * Créer un devis
     * 1/ à partir de l'artisan
     * 2/ à partir d'une demande de devis
     *
     * @param instance
     * @return
     */
    @Override
    public ResponseDN execute(RequestDN instance) throws Exception {
        ResponseDN<DevisDN> response = new ResponseDN<>();

        DataProviderManager dpm = this.transactionManager.createDataProviderManager(instance.getDataProviderManager());
        instance.setDataProviderManager(dpm);

        try {

            transactionManager.begin(dpm);

            TYPE_CREATION_DEVIS tcd = (TYPE_CREATION_DEVIS) instance.getAdditionalProperties().get(KEY_TYPE_CREATION_DEVIS);
            switch (tcd) {
                case FROM_ARTISAN:
                    break;
                case FROM_DEMANDE_DE_DEVIS:
                    break;
            }
        } finally {
            transactionManager.close(dpm);
        }

        return response;
    }
}

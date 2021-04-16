package usecases.personnes.clients;

import aop.Transactional;
import domains.devis.DevisDN;
import domains.personnes.clients.ClientDN;
import exceptions.CleanException;
import models.Precondition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.transactions.TransactionManagerPT;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;

import java.util.Objects;

import static localizations.MessageKeys.ARG_IS_REQUIRED;

public class ShareInfosDevisUE extends AbstractUsecase {

    private static final Logger LOG = LoggerFactory.getLogger(ShareInfosDevisUE.class);

    SaveClientUE saveClientUE;
    FindByEmailUE findClientByEmailUE;

    public ShareInfosDevisUE(LocalizeServicePT ls, TransactionManagerPT transactionManager, SaveClientUE saveClientUE, FindByEmailUE findClientByEmailUE) {
        super(ls, transactionManager);
        this.saveClientUE = saveClientUE;
        this.findClientByEmailUE = findClientByEmailUE;
    }

    @Transactional
    public ClientDN execute(DataProviderManager dpm, DevisDN devis) throws CleanException {

        Precondition.validate(
                Precondition.init(ls.getMsg(ARG_IS_REQUIRED, "devis"), Objects.nonNull(devis))
        );

        String emailClient = devis.getClientEmail();
        ClientDN client = new ClientDN();
        if (StringUtils.isNotEmpty(emailClient)) {
            ClientDN clientStore = findClientByEmailUE.execute(dpm, emailClient);
            if (Objects.nonNull(clientStore)) {
                client.setId(clientStore.getId());
            }
        }


        client.setArtisan(devis.getArtisan());
        client.setNom(devis.getClientNom());
        client.setPrenom(devis.getClientPrenom());
        client.setAdresse(devis.getClientAdresse());
        client.setVille(devis.getClientVille());
        client.setCodePostal(devis.getClientCodePostal());
        client.setTelephone(devis.getClientTelephone());
        client.setEmail(devis.getClientEmail());
        client.setSignature(devis.getClientSignature());
        client.setSiret(devis.getClientSiret());
        client.setSociete(devis.getClientSociete());
        client.setFonction(devis.getClientFonction());

        client = saveClientUE.execute(dpm, client);

        return client;
    }


}

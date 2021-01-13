package usecases.login;

import domains.*;
import exceptions.CleanException;
import exceptions.TechnicalException;
import models.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.repositories.ConditionDeReglementRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.RoleRepoPT;
import ports.repositories.TaxeRepoPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.clients.EnregistrerClientUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.SERVER_ERROR;

public class LoginUE extends AbstractUsecase {

    ILoginPT loginPT;
    PersonneRepoPT personneRepo;
    EnregistrerClientUE enregistrerClientUE;
    EnregistrerArtisanUE enregistrerArtisanUE;
    ConditionDeReglementRepoPT conditionDeReglementRepo;
    TaxeRepoPT taxeRepo;
    RoleRepoPT roleRepo;

    public LoginUE(LocalizeServicePT localizeService,
                   TransactionManagerPT transactionManager,
                   ILoginPT loginPT,
                   PersonneRepoPT personneRepo,
                   EnregistrerClientUE enregistrerClientUE,
                   EnregistrerArtisanUE enregistrerArtisanUE,
                   ConditionDeReglementRepoPT conditionDeReglementRepo,
                   TaxeRepoPT taxeRepo,
                   RoleRepoPT roleRepo
    ) {
        super(localizeService, transactionManager);
        this.loginPT = loginPT;
        this.personneRepo = personneRepo;
        this.enregistrerClientUE = enregistrerClientUE;
        this.enregistrerArtisanUE = enregistrerArtisanUE;
        this.conditionDeReglementRepo = conditionDeReglementRepo;
        this.taxeRepo = taxeRepo;
        this.roleRepo = roleRepo;
    }


    /**
     * Récupérer l'authorization
     * Si l'utilisateur n'existe pas dans la source de données, on le créé en tant qu'artisan ou client
     * On retourne un token avec les roles associés à l'utilisateur
     * @param dpm
     * @param loginManager
     * @return
     * @throws Exception
     */
    public AuthorizationDN execute(DataProviderManager dpm, LoginManager loginManager) throws CleanException {

        try {

            Precondition.validate(
                    Precondition.init("Le parametre loginManager ne doit pas être nul.", Objects.nonNull(loginManager)),
                    Precondition.init("Le parametre type_personne ne doit pas être nul.", Objects.nonNull(loginManager) && Objects.nonNull(loginManager.getTypePersonne()))
            );

            AuthorizationDN authorization = loginPT.getAuthorization(loginManager);

            dpm = this.transactionManager.createDataProviderManager(dpm);

            this.transactionManager.begin(dpm);


            PersonneDN personne = personneRepo.findByEmail(dpm, authorization.getEmail());

            if (Objects.isNull(personne)) {

                switch (loginManager.getTypePersonne()) {
                    case CLIENT:
                        ClientDN client = initClient(authorization);
                        client = this.enregistrerClientUE.execute(dpm, client);
                        personne = client.getPersonne();
                        break;
                    case ARTISAN:
                        ArtisanDN artisan = initArtisan(dpm, authorization);
                        artisan = this.enregistrerArtisanUE.execute(dpm, artisan);
                        personne = artisan.getPersonne();
                        break;
                }
            }

            List<String> roles = getRoles(dpm, personne);
            String token = loginPT.generateToken(personne, roles);
            authorization.setToken(token);

            this.transactionManager.commit(dpm);

            return authorization;
        } catch (Exception ex) {
            this.transactionManager.rollback(dpm);
            throw new TechnicalException(ex.getMessage(), ex, SERVER_ERROR, new String[]{ex.getMessage()});
        } finally {
            this.transactionManager.close(dpm);
        }
    }

    private List<String> getRoles(DataProviderManager dpm, PersonneDN personne) {
        List<String> roles = new ArrayList<>();

        List<RoleDN> roleList = this.roleRepo.findByPersonne(dpm, personne);

        if (CollectionUtils.isNotEmpty(roleList)) {
            roleList.forEach(item -> roles.add(item.getNom()));
        }

        return roles;
    }

    private ClientDN initClient(AuthorizationDN authorization) {

        ClientDN client = new ClientDN(initPersonne(authorization));
        return client;
    }

    private PersonneDN initPersonne(AuthorizationDN authorization) {

        PersonneDN personne = new PersonneDN();
        personne.setEmail(authorization.getEmail());
        personne.setAdresse("");
        personne.setCodePostal("");
        personne.setFonction("");
        personne.setNom(authorization.getNom());
        personne.setPrenom(authorization.getPrenom());
        personne.setSociete("");
        personne.setVille("");
        personne.setNumeroTelephone("");

        return personne;

    }

    private ArtisanDN initArtisan(DataProviderManager dpm, AuthorizationDN authorization) {


        ArtisanDN artisan = new ArtisanDN();
        artisan.setPersonne(initPersonne(authorization));

        artisan.setApplication(null);

        ConditionDeReglementDN conditionDeReglement = conditionDeReglementRepo.findFirst(dpm);
        artisan.setConditionDeReglement(conditionDeReglement);

        TaxeDN taxe = taxeRepo.findFirst(dpm);
        artisan.setTaxe(taxe);

        artisan.setLogo("");
        artisan.setProvision(BigDecimal.ZERO);
        artisan.setSignature("");
        artisan.setSiret("");
        artisan.setValiditeDevisMois(3);


        return artisan;
    }

}

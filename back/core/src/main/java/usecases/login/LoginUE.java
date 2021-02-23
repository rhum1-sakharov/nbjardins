package usecases.login;

import aop.Transactionnal;
import domains.*;
import exceptions.CleanException;
import exceptions.TechnicalException;
import models.Precondition;
import org.apache.commons.collections4.CollectionUtils;
import ports.localization.LocalizeServicePT;
import ports.login.ILoginPT;
import ports.transactions.TransactionManagerPT;
import security.LoginManager;
import transactions.DataProviderManager;
import usecases.AbstractUsecase;
import usecases.personnes.artisans.EnregistrerArtisanUE;
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.clients.EnregistrerClientUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.roles.FindByPersonneUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.SERVER_ERROR;

public class LoginUE extends AbstractUsecase {

    ILoginPT loginPT;
    EnregistrerClientUE enregistrerClientUE;
    EnregistrerArtisanUE enregistrerArtisanUE;
    FindAllConditionReglementUE findAllConditionReglementUE;
    FindAllTaxeUE findAllTaxeUE;
    FindByEmailUE artisanFindByEmailUE;
    FindByPersonneUE rolesFindByPersonne;
    usecases.personnes.FindByEmailUE personneFindByEmailUE;

    public LoginUE(LocalizeServicePT localizeService,
                   TransactionManagerPT transactionManager,
                   ILoginPT loginPT,
                   usecases.personnes.FindByEmailUE personneFindByEmailUE,
                   EnregistrerClientUE enregistrerClientUE,
                   EnregistrerArtisanUE enregistrerArtisanUE,
                   FindAllConditionReglementUE findAllConditionReglementUE,
                   FindAllTaxeUE findAllTaxeUE,
                   FindByEmailUE artisanFindByEmailUE,
                   FindByPersonneUE rolesFindByPersonne
    ) {
        super(localizeService, transactionManager);
        this.loginPT = loginPT;
        this.enregistrerClientUE = enregistrerClientUE;
        this.enregistrerArtisanUE = enregistrerArtisanUE;
        this.findAllConditionReglementUE = findAllConditionReglementUE;
        this.findAllTaxeUE = findAllTaxeUE;
        this.artisanFindByEmailUE = artisanFindByEmailUE;
        this.rolesFindByPersonne = rolesFindByPersonne;
        this.personneFindByEmailUE = personneFindByEmailUE;
    }


    /**
     * Récupérer l'authorization
     * Si l'utilisateur n'existe pas dans la source de données, on le créé en tant qu'artisan ou client
     * On retourne un token avec les roles associés à l'utilisateur
     *
     * @param dpm
     * @param loginManager
     * @return
     * @throws Exception
     */
    @Transactionnal
    public AuthorizationDN execute(DataProviderManager dpm, LoginManager loginManager) throws CleanException {

        try {

            Precondition.validate(
                    Precondition.init("Le parametre loginManager ne doit pas être nul.", Objects.nonNull(loginManager)),
                    Precondition.init("Le parametre type_personne ne doit pas être nul.", Objects.nonNull(loginManager) && Objects.nonNull(loginManager.getTypePersonne()))
            );

            AuthorizationDN authorization = loginPT.getAuthorization(loginManager);


            PersonneDN personne = personneFindByEmailUE.execute(dpm, authorization.getEmail());
            String idPersonne = null;
            if (Objects.nonNull(personne)) {
                idPersonne = personne.getId();
            }


            switch (loginManager.getTypePersonne()) {
                case CLIENT:
                    ClientDN client = initClient(idPersonne, authorization);
                    client = this.enregistrerClientUE.execute(dpm, client);
                    personne = client.getPersonne();
                    break;
                case ARTISAN:
                    ArtisanDN artisan = initArtisan(idPersonne, dpm, authorization);
                    artisan = this.enregistrerArtisanUE.execute(dpm, artisan);
                    personne = artisan.getPersonne();
                    break;
            }


            List<String> roles = getRoles(dpm, personne);
            String token = loginPT.generateToken(loginManager, personne, roles);
            authorization.setToken(token);

            return authorization;
        } catch (Exception ex) {
            throw new TechnicalException(ex.getMessage(), ex, SERVER_ERROR, new String[]{ex.getMessage()});
        }
    }

    private List<String> getRoles(DataProviderManager dpm, PersonneDN personne) throws CleanException {
        List<String> roles = new ArrayList<>();

        List<RoleDN> roleList = rolesFindByPersonne.execute(dpm, personne);

        if (CollectionUtils.isNotEmpty(roleList)) {
            roleList.forEach(item -> roles.add(item.getNom()));
        }

        return roles;
    }

    private ClientDN initClient(String idPersonne, AuthorizationDN authorization) {

        ClientDN client = new ClientDN(initPersonne(idPersonne, authorization));
        return client;
    }

    private PersonneDN initPersonne(String idPersonne, AuthorizationDN authorization) {

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

        if (Objects.nonNull(idPersonne)) {
            personne.setId(idPersonne);
        }

        return personne;

    }

    private ArtisanDN initArtisan(String idPersonne, DataProviderManager dpm, AuthorizationDN authorization) throws CleanException {


        ArtisanDN artisan = new ArtisanDN();
        artisan.setPersonne(initPersonne(idPersonne, authorization));
        artisan.setLogo(authorization.getPicture());


        ArtisanDN artisanDb = artisanFindByEmailUE.execute(dpm, artisan.getPersonne().getEmail());
        // modification
        if (Objects.nonNull(artisanDb)) {
            artisan.setId(artisanDb.getId());
            artisan.setSignature(artisanDb.getSignature());
            artisan.setSiret(artisanDb.getSiret());
            artisan.setProvision(artisanDb.getProvision());
            artisan.setValiditeDevisMois(artisanDb.getValiditeDevisMois());
            artisan.setConditionDeReglement(artisanDb.getConditionDeReglement());
            artisan.setTaxe(artisanDb.getTaxe());
        }
        // creation
        else {
            artisan.setProvision(BigDecimal.ZERO);
            artisan.setSignature("");
            artisan.setSiret("");
            artisan.setValiditeDevisMois(3);
            List<ConditionDeReglementDN> conditionDeReglementList = findAllConditionReglementUE.execute(dpm);
            artisan.setConditionDeReglement(conditionDeReglementList.get(0));
            List<TaxeDN> taxeList = findAllTaxeUE.execute(dpm);
            artisan.setTaxe(taxeList.get(0));
            artisan.setApplication(null);

        }


        return artisan;
    }

}

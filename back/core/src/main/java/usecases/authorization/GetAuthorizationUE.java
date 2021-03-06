package usecases.authorization;

import aop.Transactionnal;
import domains.authorizations.AuthorizationDN;
import domains.personnes.PersonneDN;
import domains.personnes.artisans.ArtisanDN;
import domains.personnes.clients.ClientDN;
import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import domains.referentiel.roles.RoleDN;
import domains.referentiel.taxes.TaxeDN;
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
import usecases.personnes.artisans.FindByEmailUE;
import usecases.personnes.artisans.SaveArtisanUE;
import usecases.personnes.clients.SaveClientUE;
import usecases.referentiel.conditions.reglements.FindAllConditionReglementUE;
import usecases.referentiel.roles.FindByPersonneUE;
import usecases.referentiel.taxes.FindAllTaxeUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static localizations.MessageKeys.SERVER_ERROR;

public class GetAuthorizationUE extends AbstractUsecase {

    ILoginPT loginPT;
    SaveClientUE saveClientUE;
    SaveArtisanUE saveArtisanUE;
    FindAllConditionReglementUE findAllConditionReglementUE;
    FindAllTaxeUE findAllTaxeUE;
    FindByEmailUE artisanFindByEmailUE;
    FindByPersonneUE rolesFindByPersonne;
    usecases.personnes.FindByEmailUE personneFindByEmailUE;

    public GetAuthorizationUE(LocalizeServicePT localizeService,
                              TransactionManagerPT transactionManager,
                              ILoginPT loginPT,
                              usecases.personnes.FindByEmailUE personneFindByEmailUE,
                              SaveClientUE saveClientUE,
                              SaveArtisanUE saveArtisanUE,
                              FindAllConditionReglementUE findAllConditionReglementUE,
                              FindAllTaxeUE findAllTaxeUE,
                              FindByEmailUE artisanFindByEmailUE,
                              FindByPersonneUE rolesFindByPersonne
    ) {
        super(localizeService, transactionManager);
        this.loginPT = loginPT;
        this.saveClientUE = saveClientUE;
        this.saveArtisanUE = saveArtisanUE;
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


            switch (loginManager.getTypePersonne()) {
                case CLIENT:
                    ClientDN client = initClient( authorization);
                    client = this.saveClientUE.execute(dpm, client);
                    personne = client.getPersonne();
                    break;
                case ARTISAN:
                    ArtisanDN artisan = initArtisan(personne, dpm, authorization);
                    artisan = this.saveArtisanUE.execute(dpm, artisan);
                    personne = artisan.getPersonne();
                    break;
            }


            List<String> roles = getRoles(dpm, personne);
            String token = loginPT.generateToken(loginManager, authorization, roles);
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

    private ClientDN initClient(AuthorizationDN authorization) {

        ClientDN client = new ClientDN(initNewPersonne(authorization));
        return client;
    }

    private PersonneDN initNewPersonne(AuthorizationDN authorization) {


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

    private ArtisanDN initArtisan(PersonneDN personne, DataProviderManager dpm, AuthorizationDN authorization) throws CleanException {


        ArtisanDN artisan = new ArtisanDN();

        if (Objects.isNull(personne)) {
            artisan.setPersonne(initNewPersonne(authorization));
        }else{
            artisan.setPersonne(personne);
        }

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

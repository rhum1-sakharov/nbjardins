package usecases.clients;

import domains.models.ClientDN;
import domains.models.PersonneDN;
import domains.models.Personne__RoleDN;
import domains.models.RoleDN;
import domains.wrapper.RequestMap;
import domains.wrapper.ResponseDN;
import enums.ROLES;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ports.localization.LocalizeServicePT;
import ports.repositories.ClientRepoPT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import ports.transactions.TransactionManagerPT;

import java.util.Objects;

import static domains.wrapper.RequestMap.REQUEST_KEY_CLIENT;
import static localizations.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;

@RunWith(MockitoJUnitRunner.class)
public class EnregistrerClientUETest {

    @Mock
    PersonneRepoPT personneRepo;



    @Mock
    PersonneRoleRepoPT personneRoleRepo;

    @Mock
    ClientRepoPT clientRepo;

    @Mock
    LocalizeServicePT localizeService;

    @Mock
    TransactionManagerPT transactionManager;

    EnregistrerClientUE enregistrerClientUE;
    RequestMap request;


    @Before
    public void setUp() throws Exception {


        this.enregistrerClientUE = new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService, clientRepo, transactionManager);
        this.request = new RequestMap(localizeService.getFrenchLocale(),null,null);

        this.request.put(REQUEST_KEY_CLIENT,initClientStub(initPersonneStub()));

    }



    @Test
    public void execute_should_return_error_when_personne_is_artisan() throws Exception {

        String errorMessage = "On ne peut pas enregistrer un client qui a un email qui est déjà utilisé par un artisan.";

        ClientDN client = (ClientDN) this.request.get(REQUEST_KEY_CLIENT);

        Mockito.when(this.personneRepo.findIdByEmail(null,client.getPersonne().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(null,client.getPersonne().getEmail(), ROLES.ROLE_ARTISAN.getValue())).thenReturn(initPersonneRoleStub());
        Mockito.when(this.localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN)).thenReturn(errorMessage);
        Mockito.when(this.clientRepo.saveByIdPersonne(null,initPersonneStub().getId())).thenReturn(initClientStub(initPersonneStub()));

        ResponseDN<ClientDN> response = this.enregistrerClientUE.execute(request);

        Assertions.assertThat(Objects.nonNull(response)).isTrue();
        Assertions.assertThat(response.getErrorMessages()).contains(errorMessage);
    }

    @Test
    public void execute_should_return_new_personne_when_personne_is_client() throws Exception {

        ClientDN client = (ClientDN) this.request.get(REQUEST_KEY_CLIENT);

        Mockito.when(personneRepo.saveClient(null,initPersonneStub())).thenReturn(initPersonneStub());
        Mockito.when(this.personneRepo.findIdByEmail(null,client.getPersonne().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(null,client.getPersonne().getEmail(), ROLES.ROLE_CLIENT.getValue())).thenReturn(initPersonneRoleStub());
        Mockito.when(this.clientRepo.saveByIdPersonne(null,initPersonneStub().getId())).thenReturn(initClientStub(initPersonneStub()));

        ResponseDN<ClientDN> response = this.enregistrerClientUE.execute(request);

        Assertions.assertThat(Objects.nonNull(response)).isTrue();
        Assertions.assertThat(response.getErrorMessages()).isEmpty();
        Assertions.assertThat(Objects.nonNull(response.getOne())).isTrue();

    }

    private static Personne__RoleDN initPersonneRoleStub() {
        Personne__RoleDN personne__roleDN = new Personne__RoleDN();
        personne__roleDN.setPersonne(initPersonneStub());
        personne__roleDN.setRole(initRoleStub());
        return personne__roleDN;
    }

    private static ClientDN initClientStub(PersonneDN personneDN) {
        ClientDN clientDN = new ClientDN(personneDN);
        return clientDN;
    }

    private static PersonneDN initPersonneStub() {
        PersonneDN personneDN = new PersonneDN();
        personneDN.setEmail("test@toto.fr");
        return personneDN;
    }

    private static RoleDN initRoleStub() {
        RoleDN roleDN = new RoleDN();
        return roleDN;
    }

}
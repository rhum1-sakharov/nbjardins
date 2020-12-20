package usecase.clients;

import domain.enums.ROLES;
import domain.models.ClientDN;
import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;
import domain.models.RoleDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
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

import static domain.localization.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;

@RunWith(MockitoJUnitRunner.class)
public class EnregistrerClientUETest {

    @Mock
    PersonneRepoPT personneRepo;

    @Mock
    PersonneRoleRepoPT personneRoleRepo;

    @Mock
    ClientRepoPT clientRepoPT;

    @Mock
    LocalizeServicePT localizeService;

    @Mock
    TransactionManagerPT transactionManager;

    EnregistrerClientUE enregistrerClientUE;
    RequestDN<ClientDN> request;


    @Before
    public void setUp()  {


        this.enregistrerClientUE = new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService, clientRepoPT, transactionManager);
        this.request = new RequestDN<>();

        this.request.setOne(initClientStub(initPersonneStub()));

    }



    @Test
    public void execute_should_return_error_when_personne_is_artisan() throws Exception {

        String errorMessage = "On ne peut pas enregistrer un client qui a un email qui est déjà utilisé par un artisan.";
        Mockito.when(this.personneRepo.findIdByEmail(null,this.request.getOne().getClient().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(null,this.request.getOne().getClient().getEmail(), ROLES.ROLE_ARTISAN.getValue())).thenReturn(initPersonneRoleStub());
        Mockito.when(this.localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN)).thenReturn(errorMessage);
        Mockito.when(this.clientRepoPT.saveByIdPersonne(null,initPersonneStub().getId())).thenReturn(initClientStub(initPersonneStub()));

        ResponseDN<ClientDN> response = this.enregistrerClientUE.execute(request);

        Assertions.assertThat(Objects.nonNull(response)).isTrue();
        Assertions.assertThat(response.getErrorMessages()).contains(errorMessage);
    }

    @Test
    public void execute_should_return_new_personne_when_personne_is_client() throws Exception {

        Mockito.when(personneRepo.saveClient(null,initPersonneStub())).thenReturn(initPersonneStub());
        Mockito.when(this.personneRepo.findIdByEmail(null,this.request.getOne().getClient().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(null,this.request.getOne().getClient().getEmail(), ROLES.ROLE_CLIENT.getValue())).thenReturn(initPersonneRoleStub());
        Mockito.when(this.clientRepoPT.saveByIdPersonne(null,initPersonneStub().getId())).thenReturn(initClientStub(initPersonneStub()));

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
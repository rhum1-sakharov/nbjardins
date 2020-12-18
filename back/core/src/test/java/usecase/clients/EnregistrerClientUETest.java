package usecase.clients;

import domain.enums.ROLES;
import domain.exceptions.PersistenceException;
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
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;

import java.util.Objects;

import static domain.localization.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;

@RunWith(MockitoJUnitRunner.class)
public class EnregistrerClientUETest {

    @Mock
    PersonneRepoPT personneRepo;

    @Mock
    PersonneRoleRepoPT personneRoleRepo;

    @Mock
    LocalizeServicePT localizeService;

    EnregistrerClientUE enregistrerClientUE;
    RequestDN<PersonneDN> request;


    @Before
    public void setUp()  {


        this.enregistrerClientUE = new EnregistrerClientUE(personneRepo, personneRoleRepo, localizeService);
        this.request = new RequestDN<>();

        this.request.setOne(initPersonneStub());

    }



    @Test
    public void execute_should_return_error_when_personne_is_artisan() throws PersistenceException {

        String errorMessage = "On ne peut pas enregistrer un client qui a un email qui est déjà utilisé par un artisan.";
        Mockito.when(this.personneRepo.findIdByEmail(this.request.getOne().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(this.request.getOne().getEmail(), ROLES.ROLE_ARTISAN.getValue())).thenReturn(initPersonneRoleStub());
        Mockito.when(this.localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN)).thenReturn(errorMessage);

        ResponseDN<PersonneDN> response = this.enregistrerClientUE.execute(request);

        Assertions.assertThat(Objects.nonNull(response)).isTrue();
        Assertions.assertThat(response.getErrorMessages()).contains(errorMessage);
    }

    @Test
    public void execute_should_return_new_personne_when_personne_is_client() throws PersistenceException {

        Mockito.when(personneRepo.saveClient(initPersonneStub())).thenReturn(initPersonneStub());
        Mockito.when(this.personneRepo.findIdByEmail(this.request.getOne().getEmail())).thenReturn("1");
        Mockito.when(this.personneRoleRepo.findByEmailAndRole(this.request.getOne().getEmail(), ROLES.ROLE_CLIENT.getValue())).thenReturn(initPersonneRoleStub());

        ResponseDN<PersonneDN> response = this.enregistrerClientUE.execute(request);

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
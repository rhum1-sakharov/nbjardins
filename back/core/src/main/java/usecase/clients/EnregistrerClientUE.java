package usecase.clients;

import domain.enums.ROLES;
import domain.exceptions.PersistenceException;
import domain.models.PersonneDN;
import domain.models.Personne__RoleDN;
import domain.wrapper.RequestDN;
import domain.wrapper.ResponseDN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.localization.LocalizeServicePT;
import ports.repositories.PersonneRepoPT;
import ports.repositories.PersonneRoleRepoPT;
import usecase.AbstractUsecase;
import usecase.IUsecase;

import java.util.Objects;

import static domain.localization.MessageKeys.ENREGISTRER_CLIENT_ERREUR_ARTISAN;
import static domain.localization.MessageKeys.JPA_ERREUR_SAUVEGARDE_CLIENT;

public final class EnregistrerClientUE extends AbstractUsecase implements IUsecase<PersonneDN> {

    private static final Logger LOG = LoggerFactory.getLogger(EnregistrerClientUE.class);

    PersonneRepoPT personneRepo;
    PersonneRoleRepoPT personneRoleRepo;


    public EnregistrerClientUE(PersonneRepoPT personneRepo, PersonneRoleRepoPT personneRoleRepo, LocalizeServicePT localizeService) {
        super(localizeService);
        this.personneRepo = personneRepo;
        this.personneRoleRepo = personneRoleRepo;
    }

    /**
     * Rechercher une personne par son email
     * Si elle existe, verifier que ce n'est pas un artisan
     * Si oui, interdire l enregistrement
     * Si non, enregistrer la personne et l'associer au role client
     */
    @Override
    public ResponseDN<PersonneDN> execute(RequestDN<PersonneDN> instance) {

        ResponseDN<PersonneDN> responseDN = new ResponseDN<>();

        try {

            boolean isArtisan = false;

            String idPersonne = personneRepo.findIdByEmail(instance.getOne().getEmail());

            // si la personne existe
            if (Objects.nonNull(idPersonne)) {
                Personne__RoleDN personne__role = personneRoleRepo.findByEmailAndRole(instance.getOne().getEmail(), ROLES.ROLE_ARTISAN.getValue());

                // si c'est un artisan
                if (Objects.nonNull(personne__role)) {
                    responseDN.addErrorMessage(localizeService.getMsg(ENREGISTRER_CLIENT_ERREUR_ARTISAN));
                    isArtisan = true;
                }
            }

            // si ce n'est pas un artisan, on l'enregistre en tant que client
            if (!isArtisan) {
                PersonneDN personne = personneRepo.saveClient(instance.getOne());
                personneRoleRepo.saveRoleClient(personne.getId());
                responseDN.setOne(personne);
            }

        } catch (PersistenceException e) {
            responseDN.addErrorMessage(localizeService.getMsg(JPA_ERREUR_SAUVEGARDE_CLIENT));
            LOG.error(e.getMessage(), e);
        }


        return responseDN;
    }
}

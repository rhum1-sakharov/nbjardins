package org.rlsv.adapters.secondaries.dataproviderjpa.repositories;

import domain.models.PersonneDN;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ClientEY;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ClientMapper;
import usecase.ports.repositories.ClientRepoPT;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Objects;

public class ClientRepoAR extends RepoAR implements ClientRepoPT {


    @Override
    public PersonneDN save(PersonneDN personneDN) {

        ClientEY clientEY = ClientMapper.INSTANCE.domainToEntity(personneDN);

        ClientEY clientEYDb = findByEmail(personneDN.getEmail());

        if (Objects.nonNull(clientEYDb)) {
            clientEY.setId(clientEYDb.getId());
        }

        save(clientEY);

        return ClientMapper.INSTANCE.entityToDomain(clientEY);
    }


    private ClientEY findByEmail(String email) {

        ClientEY  clientEY= null;

        try {
            TypedQuery<ClientEY> query = em.createQuery("SELECT c from ClientEY c where c.email=:email", ClientEY.class);
            clientEY = query.setParameter("email", email).getSingleResult();
        }catch (NoResultException nre){

        }

        return clientEY;

    }
}

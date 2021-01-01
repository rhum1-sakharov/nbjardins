package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.models.Domain;
import org.mapstruct.InheritInverseConfiguration;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Entity;

import java.util.List;

public interface ObjectMapper<E extends Entity, D extends Domain> {

    @InheritInverseConfiguration
    D entityToDomain(E entity);

    E domainToEntity(D dto);

    List<D> entitiesToDomains(List<E> entities);

    List<E> domainsToEntities(List<D> domains);

}

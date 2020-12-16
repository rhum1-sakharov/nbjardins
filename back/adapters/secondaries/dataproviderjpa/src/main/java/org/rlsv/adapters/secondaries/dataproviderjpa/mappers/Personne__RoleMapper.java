package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.Personne__RoleDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne__Role;

@Mapper(uses={})
public interface Personne__RoleMapper extends ObjectMapper<Personne__Role,Personne__RoleDN>{

    Personne__RoleMapper INSTANCE = Mappers.getMapper( Personne__RoleMapper.class );
}

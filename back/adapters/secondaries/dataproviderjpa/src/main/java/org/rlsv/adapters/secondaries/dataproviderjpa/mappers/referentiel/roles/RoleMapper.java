package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.referentiel.roles;

import domains.referentiel.roles.RoleDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.roles.Role;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface RoleMapper extends ObjectMapper<Role,RoleDN> {

    RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );
}

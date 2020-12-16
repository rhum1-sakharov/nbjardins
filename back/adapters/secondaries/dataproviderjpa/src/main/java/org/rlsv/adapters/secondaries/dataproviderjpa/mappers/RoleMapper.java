package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.RoleDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Role;

@Mapper(uses={})
public interface RoleMapper extends ObjectMapper<Role,RoleDN>{

    RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );
}

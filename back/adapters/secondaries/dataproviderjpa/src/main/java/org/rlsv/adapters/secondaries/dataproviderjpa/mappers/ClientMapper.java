package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.PersonneDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ClientEY;

@Mapper(uses={})
public interface ClientMapper  extends ObjectMapper<ClientEY,PersonneDN>{

    ClientMapper INSTANCE = Mappers.getMapper( ClientMapper.class );
}

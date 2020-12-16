package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.PersonneDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Personne;

@Mapper(uses={})
public interface PersonneMapper extends ObjectMapper<Personne,PersonneDN>{

    PersonneMapper INSTANCE = Mappers.getMapper( PersonneMapper.class );
}

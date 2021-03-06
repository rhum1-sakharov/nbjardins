package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.personnes.PersonneDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;

@Mapper(uses={})
public interface PersonneMapper extends ObjectMapper<Personne,PersonneDN>{

    PersonneMapper INSTANCE = Mappers.getMapper( PersonneMapper.class );
}

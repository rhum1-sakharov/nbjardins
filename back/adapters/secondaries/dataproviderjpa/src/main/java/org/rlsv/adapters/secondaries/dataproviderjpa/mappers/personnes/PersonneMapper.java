package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes;

import domains.personnes.PersonneDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.Personne;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface PersonneMapper extends ObjectMapper<Personne,PersonneDN> {

    PersonneMapper INSTANCE = Mappers.getMapper( PersonneMapper.class );
}

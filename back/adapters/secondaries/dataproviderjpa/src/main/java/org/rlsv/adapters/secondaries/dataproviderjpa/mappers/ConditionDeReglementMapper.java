package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.ConditionDeReglementDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ConditionDeReglement;

@Mapper(uses={})
public interface ConditionDeReglementMapper extends ObjectMapper<ConditionDeReglement, ConditionDeReglementDN>{

    ConditionDeReglementMapper INSTANCE = Mappers.getMapper( ConditionDeReglementMapper.class );
}

package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.conditions.reglements.ConditionDeReglement;

@Mapper(uses={})
public interface ConditionDeReglementMapper extends ObjectMapper<ConditionDeReglement, ConditionDeReglementDN>{

    ConditionDeReglementMapper INSTANCE = Mappers.getMapper( ConditionDeReglementMapper.class );
}

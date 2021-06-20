package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.referentiel.conditions.reglements;

import domains.referentiel.condition.reglement.ConditionDeReglementDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.conditions.reglements.ConditionDeReglement;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ConditionDeReglementMapper extends ObjectMapper<ConditionDeReglement, ConditionDeReglementDN> {

    ConditionDeReglementMapper INSTANCE = Mappers.getMapper( ConditionDeReglementMapper.class );
}

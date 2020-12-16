package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.DemandeDeDevisDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.DemandeDeDevis;

@Mapper(uses={})
public interface DemandeDeDevisMapper extends ObjectMapper<DemandeDeDevis,DemandeDeDevisDN>{

    DemandeDeDevisMapper INSTANCE = Mappers.getMapper( DemandeDeDevisMapper.class );
}

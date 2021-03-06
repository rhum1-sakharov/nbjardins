package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.devis.DevisDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;

@Mapper(uses={})
public interface DevisMapper extends ObjectMapper<Devis,DevisDN>{

    DevisMapper INSTANCE = Mappers.getMapper( DevisMapper.class );
}

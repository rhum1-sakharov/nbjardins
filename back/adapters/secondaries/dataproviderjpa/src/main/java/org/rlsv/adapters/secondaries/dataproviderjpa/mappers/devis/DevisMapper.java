package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.devis;

import domains.devis.DevisDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.Devis;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface DevisMapper extends ObjectMapper<Devis,DevisDN> {

    DevisMapper INSTANCE = Mappers.getMapper( DevisMapper.class );
}

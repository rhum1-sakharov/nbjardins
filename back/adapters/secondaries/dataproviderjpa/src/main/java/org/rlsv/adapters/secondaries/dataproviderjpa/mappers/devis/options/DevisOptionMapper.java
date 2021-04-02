package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.devis.options;

import domains.devis.options.DevisOptionDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.devis.options.DevisOption;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface DevisOptionMapper extends ObjectMapper<DevisOption,DevisOptionDN> {

    DevisOptionMapper INSTANCE = Mappers.getMapper( DevisOptionMapper.class );
}

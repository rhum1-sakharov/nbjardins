package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.TaxeDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Taxe;

@Mapper(uses={})
public interface TaxeMapper extends ObjectMapper<Taxe, TaxeDN>{

    TaxeMapper INSTANCE = Mappers.getMapper( TaxeMapper.class );
}

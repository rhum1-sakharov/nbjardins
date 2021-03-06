package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domains.referentiel.taxes.TaxeDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.referentiel.taxes.Taxe;

@Mapper(uses={})
public interface TaxeMapper extends ObjectMapper<Taxe, TaxeDN>{

    TaxeMapper INSTANCE = Mappers.getMapper( TaxeMapper.class );
}

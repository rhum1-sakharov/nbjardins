package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans.options;

import domains.personnes.artisans.options.ArtisanOptionDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.options.ArtisanOption;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ArtisanOptionMapper extends ObjectMapper<ArtisanOption,ArtisanOptionDN> {

    ArtisanOptionMapper INSTANCE = Mappers.getMapper( ArtisanOptionMapper.class );
}

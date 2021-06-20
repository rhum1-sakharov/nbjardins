package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans;

import domains.personnes.artisans.ArtisanDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.Artisan;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ArtisanMapper extends ObjectMapper<Artisan,ArtisanDN> {

    ArtisanMapper INSTANCE = Mappers.getMapper( ArtisanMapper.class );
}

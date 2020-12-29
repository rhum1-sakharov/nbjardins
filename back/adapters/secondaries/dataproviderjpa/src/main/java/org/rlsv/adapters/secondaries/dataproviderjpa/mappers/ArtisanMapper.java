package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.ArtisanDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.Artisan;

@Mapper(uses={})
public interface ArtisanMapper extends ObjectMapper<Artisan,ArtisanDN>{

    ArtisanMapper INSTANCE = Mappers.getMapper( ArtisanMapper.class );
}

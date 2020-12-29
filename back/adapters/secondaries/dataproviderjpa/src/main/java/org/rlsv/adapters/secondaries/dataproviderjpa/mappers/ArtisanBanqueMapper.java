package org.rlsv.adapters.secondaries.dataproviderjpa.mappers;

import domain.models.ArtisanBanqueDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.ArtisanBanque;

@Mapper(uses={})
public interface ArtisanBanqueMapper extends ObjectMapper<ArtisanBanque,ArtisanBanqueDN>{

    ArtisanBanqueMapper INSTANCE = Mappers.getMapper( ArtisanBanqueMapper.class );
}
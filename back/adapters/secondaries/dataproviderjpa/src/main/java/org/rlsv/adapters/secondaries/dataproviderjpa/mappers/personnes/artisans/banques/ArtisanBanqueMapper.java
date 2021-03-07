package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.artisans.banques;

import domains.personnes.artisans.ArtisanBanqueDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.artisans.banques.ArtisanBanque;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ArtisanBanqueMapper extends ObjectMapper<ArtisanBanque,ArtisanBanqueDN> {

    ArtisanBanqueMapper INSTANCE = Mappers.getMapper( ArtisanBanqueMapper.class );
}

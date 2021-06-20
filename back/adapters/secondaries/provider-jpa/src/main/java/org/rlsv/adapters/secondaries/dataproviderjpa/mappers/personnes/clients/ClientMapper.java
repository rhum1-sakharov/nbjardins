package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.personnes.clients;

import domains.personnes.clients.ClientDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.personnes.clients.Client;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ClientMapper extends ObjectMapper<Client,ClientDN> {

    ClientMapper INSTANCE = Mappers.getMapper( ClientMapper.class );
}

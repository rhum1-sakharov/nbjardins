package org.rlsv.adapters.secondaries.dataproviderjpa.mappers.produits;

import domains.produits.ProduitDN;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.rlsv.adapters.secondaries.dataproviderjpa.entities.produits.Produit;
import org.rlsv.adapters.secondaries.dataproviderjpa.mappers.ObjectMapper;

@Mapper(uses={})
public interface ProduitMapper extends ObjectMapper<Produit,ProduitDN> {

    ProduitMapper INSTANCE = Mappers.getMapper( ProduitMapper.class );
}

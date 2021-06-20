package org.rlsv.adapters.secondaries.dataproviderjpa.enums;

import lombok.Getter;

public enum TypeConvertMapperEnum {

    ENTITY_TO_DOMAIN("entityToDomain"),
    ENTITIES_TO_DOMAINS("entitiesToDomains"),
    DOMAIN_TO_ENTITY("domainToEntity"),
    DOMAINS_TO_ENTITIES("domainsToEntities");

    @Getter
    private final String value;

    TypeConvertMapperEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}

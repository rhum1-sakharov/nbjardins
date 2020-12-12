package org.rlsv.adapters.secondaries.dataproviderjpa.entities;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Entity {


    @Id
    private String id;

}

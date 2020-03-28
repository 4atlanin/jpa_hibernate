package com.jpa.hibernate.sample.table.embedded.entity;

import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class EmbeddedEntityOwner
{
    @Id
    private int id;

    @Embedded
    private EmbeddedEntity embeddedEntity;

}

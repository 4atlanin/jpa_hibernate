package com.jpa.hibernate.sample.entity.table.embedded.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Embeddable
public class EmbeddedEntity
{
    @Column( name = "field_from_embedded_entity" )
    private String fieldFromEmbeddedEntity;

    @Embedded
    private EmbeddedEntityGoDeeper goDeeper;

}

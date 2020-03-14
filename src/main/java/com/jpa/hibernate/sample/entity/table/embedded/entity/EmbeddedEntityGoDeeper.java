package com.jpa.hibernate.sample.entity.table.embedded.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class EmbeddedEntityGoDeeper
{
    @Column( name = "deep_field" )
    private String deepField;
}

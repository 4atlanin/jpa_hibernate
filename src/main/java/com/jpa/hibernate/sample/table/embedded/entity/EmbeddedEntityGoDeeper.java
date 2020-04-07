package com.jpa.hibernate.sample.table.embedded.entity;

import lombok.Data;

import javax.persistence.Column;

@Data
public class EmbeddedEntityGoDeeper
{
    @Column( name = "deep_field" )
    private String deepField;

    @Column( name = "this_column_should_be_replaced" )
    private String columnWhichShouldBeReplaced;
}

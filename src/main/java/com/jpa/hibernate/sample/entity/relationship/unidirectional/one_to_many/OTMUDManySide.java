package com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_many;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity( name = "otm_ud_many" )
public class OTMUDManySide
{
    @Id
    @GeneratedValue
    @Column( name = "otmudt_id" )
    private int id;

    @Column(name = "otmudt_payload")
    private String payload;

}

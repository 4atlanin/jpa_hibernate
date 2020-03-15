package com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "oto_ud_two" )
public class EntityOTOUDTwo
{

    @Id
    @Column( name = "otout_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @Column( name = "otoud_payload" )
    private String payload;
}

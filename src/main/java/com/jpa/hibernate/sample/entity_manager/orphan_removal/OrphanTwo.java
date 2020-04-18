package com.jpa.hibernate.sample.entity_manager.orphan_removal;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "orphan_two" )
public class OrphanTwo
{

    @Id
    @Column( name = "ot_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @Column( name = "ot_payload" )
    private String payload;
}

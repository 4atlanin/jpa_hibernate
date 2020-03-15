package com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "oto_ud_one" )
public class EntityOTOUDOne
{

    @Id
    @Column( name = "otouo_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    /**
     * Значит, что в этом классе будет фк
     */
    @OneToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "join_column", nullable = false )
    private EntityOTOUDTwo two;
}


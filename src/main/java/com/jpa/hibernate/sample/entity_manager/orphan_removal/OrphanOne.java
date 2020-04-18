package com.jpa.hibernate.sample.entity_manager.orphan_removal;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table( name = "orphan_one" )
public class OrphanOne
{

    @Id
    @Column( name = "oo_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    /**
     * Значит, что в этом классе будет фк
     */
    @OneToOne( fetch = FetchType.LAZY, orphanRemoval = true )
    @JoinColumn( name = "join_column" )
    private OrphanTwo two;
}
package com.jpa.hibernate.sample.entity.relationship.one_to_one.bidirectional;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table( name = "entity_one" )
public class EntityOne
{
    @Id
    @Column( name = "eo_id" )
    private long id;

    @OneToOne
    @ToString.Exclude
    @JoinColumn( name = "et_id_join_column" )
    private EntityTwo two;
}

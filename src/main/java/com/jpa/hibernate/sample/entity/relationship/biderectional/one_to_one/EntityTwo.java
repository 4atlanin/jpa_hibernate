package com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table( name = "entity_two" )
public class EntityTwo
{
    @Id
    @Column( name = "et_id" )
    private long id;

    @ToString.Exclude
    @OneToOne( mappedBy = "two" )
    private EntityOne one;
}
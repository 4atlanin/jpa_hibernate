package com.jpa.hibernate.sample.relationship.order.by;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table( name = "location_order_by" )
public class Location
{
    @Id
    @GeneratedValue
    @Column( name = "lob_id" )
    private int id;
    @Column( name = "lob_name" )
    private String name;

    public Location( String name )
    {
        this.name = name;
    }
}

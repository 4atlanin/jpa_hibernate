package com.jpa.hibernate.sample.relationship.order.column;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@javax.persistence.Table( name = "table_with_order_column" )
public class Table   //todo rename. Имелось ввиду стол, а не таблица
{
    @Id
    @GeneratedValue
    @Column( name = "twoc_id" )
    private int id;
    @Column( name = "twoc_name" )
    private String name;

    public Table( String name )
    {
        this.name = name;
    }
}

package com.jpa.hibernate.sample.inheritance.single_table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue( "C" )
public class CD extends Item
{
    @Column( name = "number_of_records" )
    private int numberOfRecords;
    @Column( name = "band" )
    private String band;
}

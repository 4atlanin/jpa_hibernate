package com.jpa.hibernate.sample.inheritance.single_table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorValue( "B" )
public class Book extends Item
{
    @Column( name = "title" )
    private String title;
}

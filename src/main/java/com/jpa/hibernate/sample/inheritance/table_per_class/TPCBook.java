package com.jpa.hibernate.sample.inheritance.table_per_class;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "tpc_book" )
@NoArgsConstructor
@DiscriminatorValue( "B" )
public class TPCBook extends TPCItem
{
    @Column( name = "title" )
    private String title;
}

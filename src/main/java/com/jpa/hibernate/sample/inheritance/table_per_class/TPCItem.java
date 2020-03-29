package com.jpa.hibernate.sample.inheritance.table_per_class;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table( name = "tpc_item" )
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS )
@DiscriminatorColumn( name = "discriminator_column", discriminatorType = DiscriminatorType.CHAR )
@DiscriminatorValue( "I" )   // В базовом классе указываем, если будем его хранить, а не просто от него наследовать
public class TPCItem
{
    @Id
    @GeneratedValue
    private int id;

    @Column( name = "name" )
    private String name;
    @Column( name = "price" )
    private int price;
    @Column( name = "description" )
    private String description;
}

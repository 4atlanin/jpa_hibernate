package com.jpa.hibernate.sample.inheritance.single_table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@DiscriminatorColumn( name = "discriminator_column", discriminatorType = DiscriminatorType.CHAR )
@DiscriminatorValue( "I" )   // В базовом классе указываем, если будем его хранить, а не просто от него наследовать
public class Item
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

package com.jpa.hibernate.sample.inheritance.mapped_super_class;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "extender_mapped_super_class")
// @AttributeOverride переопределит настройки колонки в базовом классе
@AttributeOverride( name = "columnToOverride", column = @Column( name = "overridden_column_in_the_mapped_class" ) )
public class ExtenderClass extends MappedClass
{
    @Id
    @GeneratedValue
    private int id;

    @Column( name = "extender_column" )
    private String outerField;
}

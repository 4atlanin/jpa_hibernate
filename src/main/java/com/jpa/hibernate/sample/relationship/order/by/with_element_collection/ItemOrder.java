package com.jpa.hibernate.sample.relationship.order.by.with_element_collection;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table( name = "item_orded" )
public class ItemOrder
{
    @Id
    @GeneratedValue
    @Column( name = "i_id" )
    private int id;

    @Column( name = "i_name" )
    private String name;

    @ElementCollection
    @CollectionTable( name = "simple_image" )
    @Column( name = "si_file_name" )
    @OrderBy( "si_file_name DESC" )  // сортирует по si_file_name DESC
    private List<String> images;

    @ElementCollection
    @CollectionTable( name = "embeddable" )
    @OrderBy( "fieldFromEmbeddedEntity ASC" )
    private List<FileName> embeddable;
}

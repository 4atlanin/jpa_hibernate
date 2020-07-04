package com.jpa.hibernate.sample.relationship.order.by.with_element_collection;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class FileName
{
    @Parent    // должна указывать на родителя @ElementCollection, тогда тут можно будет взять Id, для правильного использования в equals and hashcode.
                // !!! Но нужно быть осторожным и учитывать что, ItemOrder перед сохранием может не иметь id
    @ToString.Exclude
    protected ItemOrder order;
    @Column( name = "field_from_embedded_entity" )
    private String fieldFromEmbeddedEntity;
}

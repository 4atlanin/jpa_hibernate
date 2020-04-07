package com.jpa.hibernate.sample.table.embedded.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class EmbeddedEntity
{
    @Column( name = "field_from_embedded_entity" )
    private String fieldFromEmbeddedEntity;

    //Чтобы переопределить настройки колонки из @Embeddable, нужно ставить @AttributeOverride именно так как сейчас. Не над классом, и не над Энтитей.
    @Embedded
    @AttributeOverrides( {
                             @AttributeOverride( name = "columnWhichShouldBeReplaced", column = @Column( name = "overrided_attribute_from_embedded" ) )
                         } )
    private EmbeddedEntityGoDeeper goDeeper;

}

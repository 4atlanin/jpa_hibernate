package com.jpa.hibernate.sample.table.embedded.entity;

import lombok.Data;

import javax.persistence.*;

//@AttributeOverride заменит определение колонки из Embedded, на то, что в аттрибуте 'column'
@Data
@Entity
public class EmbeddedEntityOwner
{
    @Id
    private int id;

    @Embedded
    private EmbeddedEntity embeddedEntity;

}

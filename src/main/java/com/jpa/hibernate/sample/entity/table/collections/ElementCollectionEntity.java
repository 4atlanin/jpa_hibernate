package com.jpa.hibernate.sample.entity.table.collections;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table( name = "collection_owner" )
public class ElementCollectionEntity
{
    @Id
    @Column( name = "ec_id" )
    private int id;

    // @ElementCollection Создаёт дополнительную таблицу, в которой будут лежать значения из листа.
    // строка состоит из id и одного элемента коллекции.
    // @CollectionTable настраивает параметры доп таблицы, которая хранит коллекцию
    // @Column задаёт имя колонки, которая хранит значение из коллекции
    // @JoinColumn задаёт имя колонки, которая хранит id(FK) указывающий на "collection_owner"
    // FK создаётся по умолчанию, но с рандомным именем, поэтому юзаем  @ForeignKey
    @ElementCollection( fetch = FetchType.LAZY )
    @CollectionTable( name = "collection_slave", joinColumns = @JoinColumn( name = "ec_id_join_column" ),
                      foreignKey = @ForeignKey( name = "fk_element_collection" ) )
    @Column( name = "column_annotation" )
    private List<String> info = new ArrayList<>();
}

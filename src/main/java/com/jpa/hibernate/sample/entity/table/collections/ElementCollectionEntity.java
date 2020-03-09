package com.jpa.hibernate.sample.entity.table.collections;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @CollectionTable( name = "collection_list_slave", joinColumns = @JoinColumn( name = "ec_id_collection_slave_column" ),
                      foreignKey = @ForeignKey( name = "fk_list_element_collection" ) )
    @Column( name = "list_column_annotation" )
    private List<String> list = new ArrayList<>();

    // @ElementCollection Создаёт дополнительную таблицу, в которой будут лежать значения из Мапы.
    // Таблица будет иметь составной PK. "ec_id_map_join_column" + "map_key"
    // строка состоит из id, Key  и Value.
    // @MapKeyColumn - настраивает колонку для Key. По дефолту имя колонки ключа "ИмяПоля_KEY"
    // @Column - настраивает колонку для Value, которая хранит Value из Мапы. По дефолту имя колонки ключа "ИмяПоля_VALUE"
    @ElementCollection( fetch = FetchType.LAZY )
    @CollectionTable( name = "collection_map_slave", joinColumns = @JoinColumn( name = "ec_id_map_join_column" ),
                      foreignKey = @ForeignKey( name = "fk_map_element_collection" ) )
    @Column( name = "map_value" )
    @MapKeyColumn(name = "map_key")
    private Map<String, String> map = new HashMap<>();
}

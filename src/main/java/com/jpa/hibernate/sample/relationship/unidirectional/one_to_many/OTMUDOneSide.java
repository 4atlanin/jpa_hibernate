package com.jpa.hibernate.sample.relationship.unidirectional.one_to_many;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity( name = "otm_ud_one" )
public class OTMUDOneSide
{
    @Id
    @GeneratedValue
    @Column( name = "otmudo_id" )
    private int id;

    // Пример 1
    // по умолчанию Создаст таблицу связи
    @OneToMany
    private List<OTMUDManySide> many;

    //joinColumns настраивает колонку указывающую на владельца связи, т.е. на id этой энтити(One side)
    //inverseJoinColumns настраивает колонку указывающую на владеемую энтити связи, т.е. на id противоположной сущности(Many side)
    @OneToMany
    @JoinTable( name = "one_to_many_join_table",
                joinColumns = @JoinColumn( name = "one_side_id" ),
                inverseJoinColumns = @JoinColumn( name = "many_side_id" ) )
    private List<OTMUDManySide> manyCustom;

    //Владельцем связи будет ManySide. @JoinColumn настраивает таблицу в "otm_ud_many" таблице
    // Это поведение отличается от @OneToOne, там FK будет в этой же таблице.
    @OneToMany
    @JoinColumn( name = "one_many_join_column" )
    private List<OTMUDManySide> manyWithoutThirdTable;
}

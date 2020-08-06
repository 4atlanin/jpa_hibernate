package com.jpa.hibernate.sample.relationship.biderectional.many_to_many;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table( name = "mtm_bd_right" )
public class ManyRightSide
{
    @Id
    @GeneratedValue
    @Column( name = "mtm_bd_rignt_id" )
    int id;

    @Column( name = "rightField" )
    private String rightString;

    @ToString.Exclude
    @ManyToMany( mappedBy = "rightSide" )   // эта часть по факту доступна только для чтения,
    private List<ManyLeftSide> leftSide;    // т.к. при сохранении Хибер будет анализировать содержимое ManyLeftSide#rightSide

}

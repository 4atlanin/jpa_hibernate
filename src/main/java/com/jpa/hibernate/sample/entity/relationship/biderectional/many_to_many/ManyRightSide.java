package com.jpa.hibernate.sample.entity.relationship.biderectional.many_to_many;

import lombok.Data;

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

    @ManyToMany( mappedBy = "rightSide" )
    private List<ManyLeftSide> leftSide;

}

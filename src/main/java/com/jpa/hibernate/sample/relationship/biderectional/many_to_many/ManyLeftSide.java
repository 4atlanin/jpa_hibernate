package com.jpa.hibernate.sample.relationship.biderectional.many_to_many;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table( name = "mtm_bd_left" )
public class ManyLeftSide
{
    @Id
    @GeneratedValue
    @Column( name = "mtm_bd_left_id" )
    private int id;

    @Column( name = "leftField" )
    private String leftString;


    // эта сторона будет владельцем связи
    @ManyToMany
    @JoinTable( name = "mtm_join_table",
                joinColumns = @JoinColumn( name = "mtm_bd_left_id" ),
                inverseJoinColumns = @JoinColumn( name = "mtm_bd_right_id" ) )
    private List<ManyRightSide> rightSide;
}

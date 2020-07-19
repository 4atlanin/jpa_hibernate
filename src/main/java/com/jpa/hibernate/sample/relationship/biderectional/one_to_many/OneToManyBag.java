package com.jpa.hibernate.sample.relationship.biderectional.one_to_many;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity( name = "one_to_many_bag" )
@NoArgsConstructor
public class OneToManyBag
{
    @Id
    @GeneratedValue
    @Column( name = "otmb_id" )
    private int id;

    //Владельцем связи будет ManySide. @JoinColumn настраивает таблицу в "otm_ud_many" таблице
    // Это поведение отличается от @OneToOne, там FK будет в этой же таблице.
    @OneToMany( cascade = CascadeType.PERSIST, mappedBy = "oneToManyBag" )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<ManyToOneBag> bag = new ArrayList<>();
}


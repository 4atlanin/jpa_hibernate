package com.jpa.hibernate.sample.relationship.biderectional.one_to_many;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity( name = "many_to_one_bag" )
@NoArgsConstructor
@RequiredArgsConstructor
public class ManyToOneBag
{
    @Id
    @GeneratedValue
    @Column( name = "mtob_id" )
    private int id;

    @NonNull
    @Column( name = "mtob_payload" )
    private String payload;

    @ManyToOne                      // всегда находится на владеющей стороне
    @JoinColumn( name = "otmb_join_column", nullable = false )
    private OneToManyBag oneToManyBag;
}

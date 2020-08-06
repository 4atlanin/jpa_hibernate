package com.jpa.hibernate.sample.relationship.biderectional.one_to_many;

import lombok.*;

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
    @ToString.Exclude
    private int id;

    @ToString.Exclude
    @NonNull
    @Column( name = "mtob_payload" )
    private String payload;

    @ToString.Exclude
    @ManyToOne                      // всегда находится на владеющей стороне
    //  @LazyToOne( LazyToOneOption.NO_PROXY )
    @JoinColumn( name = "otmb_join_column", nullable = false )
    private OneToManyBag oneToManyBag;
}

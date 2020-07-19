package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table( name = "address_pk_join" )
public class AddressPkJoinColumnMaster
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @NonNull
    @Column( name = "apj_street" )
    private String street;
}

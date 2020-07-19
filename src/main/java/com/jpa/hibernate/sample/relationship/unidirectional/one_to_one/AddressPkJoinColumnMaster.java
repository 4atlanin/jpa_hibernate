package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table( name = "address_pk_join_column" )
public class AddressPkJoinColumnMaster
{
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @NonNull
    @Column( name = "apjc_street" )
    private String street;
}

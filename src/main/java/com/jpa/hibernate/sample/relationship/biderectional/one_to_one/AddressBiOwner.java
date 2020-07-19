package com.jpa.hibernate.sample.relationship.biderectional.one_to_one;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table( name = "address_bi_owner" )
public class AddressBiOwner
{
    @Id
    @GeneratedValue( generator = "addressKeyGenerator" )
    @GenericGenerator( name = "addressKeyGenerator",
                       strategy = "foreign",
                       parameters = @org.hibernate.annotations.Parameter( name = "property", value = "user" ) )
    @Column( name = "abo_id" )
    private int id;

    @NonNull
    @Column( name = "abo_street" )
    private String street;

    @OneToOne( optional = false )
    @PrimaryKeyJoinColumn
    @NonNull
    private UserBiSlave user;

}

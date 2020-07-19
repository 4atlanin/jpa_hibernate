package com.jpa.hibernate.sample.relationship.biderectional.one_to_one;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table( name = "user_bi_slave" )
@RequiredArgsConstructor
@NoArgsConstructor
public class UserBiSlave
{
    @Id
    @Column( name = "ubs_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @NonNull
    @Column( name = "ubs_name" )
    private String name;

    @OneToOne( cascade = CascadeType.PERSIST, mappedBy = "user", optional = false )
    private AddressBiOwner address;
}

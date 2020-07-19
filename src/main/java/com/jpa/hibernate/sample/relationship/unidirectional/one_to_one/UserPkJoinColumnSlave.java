package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table( name = "user_pk_join_column" )
@RequiredArgsConstructor
@NoArgsConstructor
public class UserPkJoinColumnSlave
{
    @Id
    @NonNull
    @Column( name = "upjc_id" )
    private int id;

    @NonNull
    @Column( name = "upjc_name" )
    private String name;

    @OneToOne( fetch = FetchType.LAZY, optional = false )  //по спеке Lazy не работает с optional = false. Но можно обойти ограничение через модификацию байткода
    @PrimaryKeyJoinColumn
    private AddressPkJoinColumnMaster address;
}

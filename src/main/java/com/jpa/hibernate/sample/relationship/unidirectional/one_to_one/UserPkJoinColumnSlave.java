package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table( name = "user_pk_join" )
@RequiredArgsConstructor
@NoArgsConstructor
public class UserPkJoinColumnSlave
{
    @Id
    @NonNull
    @Column( name = "upj_id" )
    private int id;

    @NonNull
    @Column( name = "upj_name" )
    private String name;

    // по спеке у @PrimaryKeyJoinColumn Lazy не работает с optional = true. Но можно обойти ограничение через модификацию байткода
    // а вот Для связи по фк (@JoinColumn) всё будет ок, т.е. владелец связи имеет fk на зависимую таблицу, и точно чзнает о существовании энтити. Это позволяет создать прокси объект.
    @OneToOne( fetch = FetchType.LAZY, optional = false )
    @PrimaryKeyJoinColumn
    private AddressPkJoinColumnMaster address;
}

package com.jpa.hibernate.sample.inheritance.polimorf_collection;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table( name = "user_" )
public class User_
{
    @Id
    @GeneratedValue
    @Column( name = "u__id" )
    protected int id;

    @Column( name = "u__name" )
    protected String name;

    @OneToMany( mappedBy = "user" )
    @ToString.Exclude                         // В отладчике пытается печатать toString значение и грузит из памяти LAZY запись.
    protected Set<BillingDetail_> defaultBilling = new HashSet<>();
}

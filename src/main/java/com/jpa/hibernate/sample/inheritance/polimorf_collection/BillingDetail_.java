package com.jpa.hibernate.sample.inheritance.polimorf_collection;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public abstract class BillingDetail_
{
    @Id
    @GeneratedValue
    @Column( name = "bd_id" )
    protected int id;

    @ManyToOne( fetch = FetchType.LAZY )
    protected User_ user;
    
    @NotNull
    @Column( name = "bd_owner" )
    protected String owner;

}

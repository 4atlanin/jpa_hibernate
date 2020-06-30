package com.jpa.hibernate.sample.inheritance.polimorf_assocaiation;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public abstract class BillingDetail
{
    @Id
    @GeneratedValue
    @Column( name = "bd_id" )
    protected int id;

    @NotNull
    @Column( name = "bd_owner" )
    protected String owner;
    
}

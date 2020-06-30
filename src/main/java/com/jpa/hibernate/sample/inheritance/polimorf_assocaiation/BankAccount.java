package com.jpa.hibernate.sample.inheritance.polimorf_assocaiation;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table( name = "bank_account" )
public class BankAccount extends BillingDetail
{
    @NotNull
    @Column( name = "ba_account" )
    protected String account;

    @NotNull
    @Column( name = "ba_name" )
    protected String name;

    @NotNull
    @Column( name = "ba_swift" )
    protected String swift;
}


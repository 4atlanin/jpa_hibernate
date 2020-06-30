package com.jpa.hibernate.sample.inheritance.polimorf_collection;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table( name = "bank_account_" )
public class BankAccount_ extends BillingDetail_
{
    @NotNull
    @Column( name = "ba__account" )
    protected String account;

    @NotNull
    @Column( name = "ba__name" )
    protected String name;

    @NotNull
    @Column( name = "ba__swift" )
    protected String swift;
}


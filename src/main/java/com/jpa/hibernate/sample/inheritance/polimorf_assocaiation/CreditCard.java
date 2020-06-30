package com.jpa.hibernate.sample.inheritance.polimorf_assocaiation;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table( name = "credit_card" )
@PrimaryKeyJoinColumn( name = "changed_pk_name_id" )   // можно поменять название PK колонки в credit_card таблице при наследовании
public class CreditCard extends BillingDetail
{
    @NotNull
    @Column( name = "cc_card_number" )
    protected String account;

    @NotNull
    @Column( name = "cc_expiration_month" )
    protected String expMonth;

    @NotNull
    @Column( name = "cc_expiration_year" )
    protected String expYear;
}

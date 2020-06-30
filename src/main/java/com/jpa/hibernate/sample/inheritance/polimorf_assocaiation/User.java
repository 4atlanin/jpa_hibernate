package com.jpa.hibernate.sample.inheritance.polimorf_assocaiation;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table( name = "user" )
public class User
{
    @Id
    @GeneratedValue
    @Column( name = "u_id" )
    protected int id;

    @Column( name = "u_name" )
    protected String name;

    @ManyToOne( fetch = FetchType.LAZY )
    @ToString.Exclude                         // В отладчике пытается печатать toString значение и грузит из памяти LAZY запись.
    protected BillingDetail defaultBilling;
}

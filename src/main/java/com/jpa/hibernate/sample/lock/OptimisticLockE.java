package com.jpa.hibernate.sample.lock;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table( name = "optimistic_lock_e" )
public class OptimisticLockE
{
    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private String value;
}

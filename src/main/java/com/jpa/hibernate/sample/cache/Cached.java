package com.jpa.hibernate.sample.cache;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table( name = "cached" )
@Data
@Cacheable( false )// не нужна для хибернейта, он её игнорит
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class Cached
{
    @Id
    @GeneratedValue
    private int id;

    private String value;

}

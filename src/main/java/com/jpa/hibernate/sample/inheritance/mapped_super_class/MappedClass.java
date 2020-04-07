package com.jpa.hibernate.sample.inheritance.mapped_super_class;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class MappedClass
{
    @Column( name = "mapped_column" )
    private String mappedColumn;

    @Column( name = "columnToOverride" )
    private String columnToOverride;
}

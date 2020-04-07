package com.jpa.hibernate.sample.inheritance.table_per_class;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "tpc_newspaper" )
@NoArgsConstructor
public class TPCNewsPaper extends TPCItem
{
    @Column( name = "pages" )
    private int pages;
}

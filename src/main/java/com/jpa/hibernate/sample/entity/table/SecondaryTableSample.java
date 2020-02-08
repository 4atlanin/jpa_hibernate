package com.jpa.hibernate.sample.entity.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*@Data
@Entity
@Table(name = "secondary_table_sample")
@NoArgsConstructor
//Каждый доступ к сущности превращается в join всех таблиц
@SecondaryTables({
        @SecondaryTable(name = "city"),
        @SecondaryTable(name = "country")})
public class SecondaryTableSample {
    @Id
    private int id;
    private String street;

    @Column(table = "city")
    private String city;

    @Column(table = "country")
    private String country;

}*/

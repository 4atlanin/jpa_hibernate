package com.jpa.hibernate.sample.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "secondary_table_sample")
@NoArgsConstructor
//Каждый доступ к сущности превращается в join всех таблиц
@SecondaryTables({
        @SecondaryTable(name = "city"),
        @SecondaryTable(name = "country")})
public class SecondaryTableSample {
    @Id
    @GeneratedValue
    @Column( name = "sts_id" )
    private Long id;

    @Column( name = "sts_street" )
    private String street;

    // table указывает к какой таблице относится аттрибут
    @Column(table = "city")
    private String city;

    @Column(table = "country")
    private String country;

}

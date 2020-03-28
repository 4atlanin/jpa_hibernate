package com.jpa.hibernate.sample.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
//Entity#name аттрибут задаёт имя энтити. Оно будет использоваться в JPQL
//и как имя таблицы по умолчанию в бд
@Entity
//@UniqueConstraint при генерации DDL создаёт UK
@Table(name = "table_sample", uniqueConstraints = {
                    @UniqueConstraint(name = "uk_id_field", columnNames = {"ts_id", "field"}),
                    @UniqueConstraint(name = "uk_field", columnNames = {"field"})})
@NoArgsConstructor
@NamedQueries( {
                   @NamedQuery( name = "findAllTableSamplesNamedQuery", query = "SELECT ts FROM TableSample ts" )
               } )
public class TableSample {
    // GenerationType.AUTO - если база поддерживает, то выбирается SEQUENCE, иначе TABLE стратегия
    @Id @GeneratedValue
    @Column(name = "ts_id")
    private Long id;

    private String field;
}

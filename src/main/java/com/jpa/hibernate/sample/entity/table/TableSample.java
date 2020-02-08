package com.jpa.hibernate.sample.entity.table;

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
public class TableSample {
    @Id @GeneratedValue
    @Column(name = "ts_id")
    private Long id;

    private String field;
}

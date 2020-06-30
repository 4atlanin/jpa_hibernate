package com.jpa.hibernate.sample.inheritance.default_behaviour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "default_extended_class" )           // такой таблицы не будет существовать, всё положится в default_base_class. По стратегии SINGLE_TABLE. Т.е. @Table бесмысленна тут
public class ExtendedClass extends BaseClass
{
    @Column( name = "dec_additional_content" )      // эта колонка засунется в default_base_class таблицу
    private String additionalContent;
}

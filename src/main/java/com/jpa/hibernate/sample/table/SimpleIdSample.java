package com.jpa.hibernate.sample.table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class SimpleIdSample {
    //может примитивом(кроме boolean, float, double)
    //или обёртка над соответствующим примитивом,
    //или массив соответствующих примитивов
    //или строка, дата, BigInteger
    @Id
    private int id;
}

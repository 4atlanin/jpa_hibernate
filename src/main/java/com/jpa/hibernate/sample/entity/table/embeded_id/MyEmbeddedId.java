package com.jpa.hibernate.sample.entity.table.embeded_id;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

// должен иметь конструктор ез аргументов, геттеры, сеттеры икуалс и хэшкод. Имплементить Serializable
@Data
@Embeddable
public class MyEmbeddedId implements Serializable {
    //todo тут может быть колумн?
    private int partOne;
    private int partTwo;
}

package com.jpa.hibernate.sample.entity.table;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class EmbeddedIdSample {
    @EmbeddedId
    private MyEmbeddedId id;
}

// должен иметь конструктор ез аргументов, геттеры, сеттеры икуалс и хэшкод. Имплементить Serializable
@Data
@Embeddable
class MyEmbeddedId implements Serializable {
    //todo тут может быть колумн?
    private int partOne;
    private int partTwo;
}

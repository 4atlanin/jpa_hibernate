package com.jpa.hibernate.sample.entity.table.embeded_id;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// должен иметь конструктор ез аргументов, геттеры, сеттеры икуалс и хэшкод. Имплементить Serializable
@Data
@Embeddable
@NoArgsConstructor
public class MyEmbeddedId implements Serializable {
    //Детали берутся отсюда
    @Column( name = "partOne_nia" )
    private int partOne;
    @Column( name = "partTwo_nia" )
    private int partTwo;
}

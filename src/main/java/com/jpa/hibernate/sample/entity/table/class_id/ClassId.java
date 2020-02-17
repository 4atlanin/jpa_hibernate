package com.jpa.hibernate.sample.entity.table.class_id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

//todo add sample about search in jpql
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassId implements Serializable {
    //эта аннотация перекрывает ту, что в энтити
    @Column( name = "partOne_nia" )
    private int partOne;
    @Column( name = "partTwo_nia" )
    private int partTwo;
}

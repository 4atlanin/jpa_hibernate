package com.jpa.hibernate.sample.entity.table;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@IdClass(ClassId.class)
public class IdClassSample {
    //Todo Can we use generator here?
    @Id int partOne;
    @Id int partTwo;
}
//todo ad sample about search in jpql
@Data
class ClassId implements Serializable {
    private int partOne;
    private int partTwo;
}

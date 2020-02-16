package com.jpa.hibernate.sample.entity.table.class_id;

import com.jpa.hibernate.sample.entity.table.class_id.ClassId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(ClassId.class)
public class IdClassSample {
    //Todo Can we use generator here?
    @Id int partOne;
    @Id int partTwo;
}

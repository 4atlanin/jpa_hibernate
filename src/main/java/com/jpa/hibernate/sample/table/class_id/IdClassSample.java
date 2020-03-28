package com.jpa.hibernate.sample.table.class_id;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(ClassId.class)
@NoArgsConstructor
public class IdClassSample {
    //Todo Can we use generator here?
    @Id
    @Column( name = "partOne" )
    int partOne;
    @Id
    @Column( name = "partTwo" )
    int partTwo;

    public ClassId getId()
    {
        return new ClassId( partOne, partTwo );
    }
}

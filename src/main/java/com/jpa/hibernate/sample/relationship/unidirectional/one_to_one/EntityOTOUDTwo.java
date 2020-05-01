package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import lombok.Data;

import javax.persistence.*;

import static com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo.*;
import static javax.persistence.ParameterMode.IN;
import static javax.persistence.ParameterMode.OUT;

// Не знал, что нужно импортировать константы, если они используются внутри этогоже фалйа, но вне класса(аннотации над классом)

@Data
@Entity
@Table( name = "oto_ud_two" )
//Есть ещё @NamedNativeQuery(-ies) для нативных запросов
//И @NamedStoredProcedureQuery(-ies) для хранимок
@NamedQueries( {
                   //Хорошая практика в название первым словом записывать имя Энтити и через точку название квери
                   @NamedQuery( name = FIND_BY_HARDCODED_PAYLOAD, query = "select e from EntityOTOUDTwo e where e.payload = 'payload'" ),
                   @NamedQuery( name = FIND_BY_PAYLOAD, query = "select e.payload from EntityOTOUDTwo e where e.payload = :payload" )
               } )
@NamedStoredProcedureQuery(name = STORED_PROCEDURE_NAMED_QUERY, procedureName =
    "simple_procedure",
                           parameters = {
                               @StoredProcedureParameter(name = "numb", mode = IN,
                                                         type = Integer.class),
                               @StoredProcedureParameter(name = "result", mode = OUT,
                                                         type = String.class)
                           }
)
public class EntityOTOUDTwo
{
    public static final String FIND_BY_PAYLOAD = "EntityOTOUDTwo.findByPayload";
    public static final String FIND_BY_HARDCODED_PAYLOAD = "EntityOTOUDTwo.findByHardcodedPayload";
    public static final String STORED_PROCEDURE_NAMED_QUERY = "EntityOTOUDTwo.storedProcedureNamedQuery";

    @Id
    @Column( name = "otout_id" )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int id;

    @Column( name = "otoud_payload" )
    private String payload;

    @Column( name = "otoud_another_string" )
    private String anotherString;
}

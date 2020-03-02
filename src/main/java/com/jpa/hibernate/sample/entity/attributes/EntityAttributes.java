package com.jpa.hibernate.sample.entity.attributes;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table( name = "entity_attributes" )
@Access( AccessType.FIELD )
public class EntityAttributes
{
    @Id
    private Integer id;

    @Lob
    @Basic( fetch = FetchType.LAZY, optional = true )       //todo не получилось тащить поле как lazy
    @Column( length = 1000 )
    private char[] lob;

    //length - только для строк
    //updatable и insertable не добавляет столбец в сгенерированный sql
    @Column( name = "column_attribute", unique = true, nullable = false,
             insertable = true, updatable = false, columnDefinition = "BIGINT(16)",
             table = "entity_attributes", length = 255, precision = 0, scale = 0 )
    private Integer columnAttribute;

    //тип колонки будет TIME
    @Temporal( TemporalType.TIME )
    private Date temporalTime;

    //тип колонки будет DATE
    @Temporal( TemporalType.DATE )
    private Date temporalDate;

    //тип колонки будет DATETIME
    @Temporal( TemporalType.TIMESTAMP )
    private Date temporalTimestamp;

    @Transient
    private String willNotBePersisted;

    @Enumerated( EnumType.ORDINAL )
    private Enumeration myEnumOrdinal;

    //todo If we use @Enumerated, hibernates converters will not work for this enum
    @Enumerated( EnumType.STRING )
    private Enumeration myEnumString;

    @Column( name = "not_work" )
    @Access( AccessType.FIELD )
    private String accessTesticulus;

    @Access( AccessType.PROPERTY )
    @Column( name = "accessTesticulus" )
    public String getAccessTesticulus()
    {
        return accessTesticulus;
    }

    public void setAccessTest( String accessTest )
    {
        this.accessTesticulus = accessTest;
    }
}
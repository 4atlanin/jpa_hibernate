package com.jpa.hibernate.sample.custom_types;

import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "basic_type" )
public class EntityForConverters
{
    @Id
    private int field;

    //@Type( type = "com.jpa.hibernate.sample.configuration.basic_conversion.MyCustomBasicStringType" )   // чтобы не указывать пакет, можно зарегать новый тип в SessionFactory
    @Type( type = "mytype_varchar" )   // чтобы не указывать пакет, можно использовать @TypeDef над классом, или можно зарегать новый тип в SessionFactory
    private MyCustomType myCustomType;

    //!!! Порядок @Column аннотации имеет значение !!!
    @Type( type = "monetary_amount_usd" )
    @Columns( columns = {
        @Column( name = "usd_amount" ),
        @Column( name = "usd_currency" )
    } )
    private MonetaryAmount amountUSD;

    //!!! Порядок @Column аннотации имеет значение !!!
    @Type( type = "monetary_amount_eur" )
    @Columns( columns = {
        @Column( name = "eur_amount" ),
        @Column( name = "eur_currency" )
    } )
    private MonetaryAmount amountEUR;

}

package com.jpa.hibernate.sample.configuration;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class NamingStrategy extends PhysicalNamingStrategyStandardImpl
{
    @Override
    public Identifier toPhysicalColumnName( Identifier name, JdbcEnvironment context )
    {
        if( name.getText().equals( "column_name_strategy" ) )
        {
            return new Identifier( "CustomTableName" + name.getText(), name.isQuoted() );
        }
        else
        {
            return new Identifier( "CustomTableName" + name.getText(), name.isQuoted() );
        }
    }
}
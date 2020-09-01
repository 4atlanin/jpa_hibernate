package com.jpa.hibernate.sample.configuration;

import org.hibernate.jdbc.Work;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Можно использовать интерфейс ReturningWork, если нужно вернуть результат выполнения работы
 */
public class QueryItemWork implements Work
{
    final protected int itemId;

    public QueryItemWork( int itemId )
    {
        this.itemId = itemId;
    }

    @Override
    public void execute( Connection connection ) throws SQLException
    {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            preparedStatement = connection.prepareStatement( "SELECT * FROM user_ where u__id = ?" );
            preparedStatement.setLong( 1, itemId );
            resultSet = preparedStatement.executeQuery();

            while( resultSet.next() )
            {
                String userName = resultSet.getString( "u__name" );
                System.out.println( "UserName is " + userName );
            }
        }
        finally
        {
            if( resultSet != null )
            {
                resultSet.close();
            }

            if( preparedStatement != null )
            {
                preparedStatement.close();
            }
        }

    }
}

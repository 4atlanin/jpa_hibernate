package com.jpa.hibernate.sample.relationship.order.column;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OrderColumnTest extends JpaHibernateBaseTest
{
    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testOrderColumn()
    {
        Room room = new Room( RandomStringUtils.randomAlphanumeric( 10 ) );
        room.setTables( getRandomTables( 10 ) );
        room = roomRepository.save( room );

        Room room2 = new Room( RandomStringUtils.randomAlphanumeric( 10 ) );
        room2.setTables( getRandomTables( 10 ) );
        room2 = roomRepository.save( room2 );
        assertNotNull( room2 );
    }

    private List<Table> getRandomTables( int amount )
    {
        List<Table> tables = new ArrayList<>();

        for( int i = 0; i < amount; i++ )
        {
            tables.add( new Table( RandomStringUtils.randomAlphanumeric( 10 ) ) );
        }

        return tables;
    }
/*
    // todo comparator will allow to get rid of code duplicates
    private boolean checkOrder( List<Table> tables )
    {
        Iterator<Table> iterator = tables.iterator();
        Table current;
        Table previous = iterator.next();

        while( iterator.hasNext() )
        {
            current = iterator.next();

            if( previous.getName().toLowerCase().charAt( 0 ) < current.getName().toLowerCase().charAt( 0 ) )
            {
                return false;
            }
        }

        return true;
    }*/
}

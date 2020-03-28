package com.jpa.hibernate.sample.relationship.order.by;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OrderByTest extends JpaHibernateBaseTest
{
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testOrderByName()
    {
        Company company = new Company( RandomStringUtils.randomAlphanumeric( 10 ) );
        company.setLocations( getRandomLocation( 10 ) );
        company = companyRepository.save( company );
        company = companyRepository.findById( company.getId() ).get();
        assertTrue( checkOrderAsc( company.getLocations() ) );
        assertFalse( checkOrderDesc( company.getLocations() ) );
    }

    private List<Location> getRandomLocation( int amount )
    {
        List<Location> locations = new ArrayList<>();

        for( int i = 0; i < amount; i++ )
        {
            locations.add( new Location( RandomStringUtils.randomAlphanumeric( 10 ) ) );
        }

        return locations;
    }

    // todo comparator will allow to get rid of code duplicates
    private boolean checkOrderAsc( List<Location> locations )
    {
        Iterator<Location> iterator = locations.iterator();
        Location current;
        Location previous = iterator.next();

        while( iterator.hasNext() )
        {
            current = iterator.next();

            if( previous.getName().toLowerCase().charAt( 0 ) > current.getName().toLowerCase().charAt( 0 ) )
            {
                System.out.print( "Previous: " + previous.getName() );
                System.out.println( ".   Current: " + previous.getName() );
                return false;
            }
        }

        return true;
    }

    // todo comparator will allow to get rid of code duplicates
    private boolean checkOrderDesc( List<Location> locations )
    {
        Iterator<Location> iterator = locations.iterator();
        Location current;
        Location previous = iterator.next();

        while( iterator.hasNext() )
        {
            current = iterator.next();

            if( previous.getName().toLowerCase().charAt( 0 ) < current.getName().toLowerCase().charAt( 0 ) )
            {
                return false;
            }
        }

        return true;
    }

}

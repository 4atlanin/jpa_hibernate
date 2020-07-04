package com.jpa.hibernate.sample.relationship.order.by;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.order.by.with_element_collection.FileName;
import com.jpa.hibernate.sample.relationship.order.by.with_element_collection.ItemOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OrderByTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testOrderWithElementCollection()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ItemOrder one = getItemWithListOfStrings();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( one );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        one = entityManager.find( ItemOrder.class, one.getId() );
        assertNotNull( one );
        transaction.commit();

        entityManager.close();
    }

    private ItemOrder getItemWithListOfStrings()
    {
        ItemOrder item = new ItemOrder();
        item.setName( "itemName" );
        item.setImages( getListStrings() );

        return item;
    }

    private List<String> getListStrings()
    {
        List<String> list = new ArrayList<>();

        list.add( "1" );
        list.add( "2" );
        list.add( "3" );

        return list;
    }

    @Test
    public void testWithEmbeddableElementCollection()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ItemOrder one = getItemWithListOfEmbeddable();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( one );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        one = entityManager.find( ItemOrder.class, one.getId() );
        assertNotNull( one );
        transaction.commit();

        entityManager.close();
    }

    private ItemOrder getItemWithListOfEmbeddable()
    {
        ItemOrder item = new ItemOrder();
        item.setName( "itemName" );
        item.setEmbeddable( getListEmbeddedEntity() );

        return item;
    }

    private List<FileName> getListEmbeddedEntity()
    {
        List<FileName> list = new ArrayList<>();

        FileName e1 = new FileName();
        e1.setFieldFromEmbeddedEntity( "a" );

        FileName e2 = new FileName();
        e2.setFieldFromEmbeddedEntity( "b" );

        list.add( e1 );
        list.add( e2 );
        return list;
    }

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

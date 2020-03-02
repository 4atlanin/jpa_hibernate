package com.jpa.hibernate.sample;

import com.jpa.hibernate.sample.entity.attributes.EntityAttributes;
import com.jpa.hibernate.sample.entity.attributes.Enumeration;
import com.jpa.hibernate.sample.repository.attributes.EntityAttributesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class EntityAttributesTest extends JpaHibernateBaseTest
{
    @PersistenceContext
    private EntityManager entityManager;

    private Random random = new Random();

    @Autowired
    private EntityAttributesRepository entityAttributesRepository;

    @Test
    public void testFetchTypeLazyForColumn()
    {
        EntityAttributes entityAttributes = new EntityAttributes();
        entityAttributes.setLob( RandomStringUtils.random( 900 ).toCharArray() );
        entityAttributes.setId( random.nextInt() );
        entityAttributes.setColumnAttribute( random.nextInt() );

        EntityAttributes retrievedEntityAttributes = entityAttributesRepository.save( entityAttributes );

        List<EntityAttributes> embeddedIdList = entityManager.createQuery( "SELECT ea FROM EntityAttributes ea" ).getResultList();
        System.out.println( embeddedIdList.get( 0 ).getLob() );
        assertNotNull( retrievedEntityAttributes );
    }

    @Test
    public void temporalTest()
    {
        EntityAttributes entityAttributes = new EntityAttributes();
        entityAttributes.setId( random.nextInt() );
        entityAttributes.setColumnAttribute( random.nextInt() );
        entityAttributes.setTemporalDate( new Date() );
        entityAttributes.setTemporalTime( new Date() );
        entityAttributes.setTemporalTimestamp( new Date() );

        EntityAttributes retrievedEntityAttributes = entityAttributesRepository.save( entityAttributes );

        assertNotNull( retrievedEntityAttributes );
    }

    @Test
    public void enumTest()
    {
        EntityAttributes entityAttributes = new EntityAttributes();
        entityAttributes.setId( random.nextInt() );
        entityAttributes.setColumnAttribute( random.nextInt() );
        entityAttributes.setMyEnumOrdinal( Enumeration.ZERO );
        entityAttributes.setMyEnumString( Enumeration.ONE );

        EntityAttributes retrievedEntityAttributes = entityAttributesRepository.save( entityAttributes );

        assertNotNull( retrievedEntityAttributes );
    }
}
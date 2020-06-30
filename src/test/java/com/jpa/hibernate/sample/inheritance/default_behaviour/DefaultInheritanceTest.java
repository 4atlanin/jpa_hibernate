package com.jpa.hibernate.sample.inheritance.default_behaviour;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class DefaultInheritanceTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testInheritanceWithSingleTable()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist( getBaseClass() );
        entityManager.persist( getExtendedClass() );
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        List<BaseClass> entities = entityManager.createQuery( "SElECT ec FROM BaseClass ec" ).getResultList();
        assertNotNull( entities );
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static BaseClass getBaseClass()
    {
        BaseClass bc = new BaseClass();
        bc.setContent( RandomStringUtils.random( 5 ) );
        return bc;
    }

    public static ExtendedClass getExtendedClass()
    {
        ExtendedClass ec = new ExtendedClass();
        ec.setAdditionalContent( RandomStringUtils.random( 5 ) );
        ec.setContent( RandomStringUtils.random( 5 ) );

        return ec;
    }

}

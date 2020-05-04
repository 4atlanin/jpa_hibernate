package com.jpa.hibernate.sample.cache;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class CacheTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    //Из этого энтити мэнеджера нельзя создавать ручные транзакции, т.к. для этого мэнеджера все транзакции управляются спрингом
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testNativeQuery()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cached two = new Cached();
        two.setValue( "lalka" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        Cache cache = entityManagerFactory.getCache();

        assertTrue( cache.contains( Cached.class, two.getId() ) );
        assertFalse( cache.contains( Cached.class, 100 ) );

        cache.evict( Cached.class, two.getId() );   //убираем из кэша по id
        assertFalse( cache.contains( Cached.class, two.getId() ) );

    }

}

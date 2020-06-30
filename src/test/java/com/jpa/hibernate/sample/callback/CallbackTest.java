package com.jpa.hibernate.sample.callback;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class CallbackTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testPrePostCallbacks()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CallbackEntity two = new CallbackEntity();
        two.setName( "lalka bigus" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        entityManager.flush();
        System.out.println( "  " );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        entityManager.merge( two );
        two.setName( "Updated name" );
        System.out.println( "  " );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        two = entityManager.find( CallbackEntity.class, two.getId() );
        System.out.println( "  " );
        transaction.commit();

        transaction.begin();
        entityManager.remove( two );
        System.out.println( "  " );
        transaction.commit();

        entityManager.close();
    }
}

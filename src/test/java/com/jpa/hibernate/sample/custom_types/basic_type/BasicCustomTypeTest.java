package com.jpa.hibernate.sample.custom_types.basic_type;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.custom_types.EntityForConverters;
import com.jpa.hibernate.sample.custom_types.MonetaryAmount;
import com.jpa.hibernate.sample.custom_types.MyCustomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicCustomTypeTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testBasicType()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityForConverters bt = new EntityForConverters();
        bt.setField( 10 );
        bt.setMyCustomType( new MyCustomType( "lalka" ) );
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( bt );
        transaction.commit();

        transaction.begin();
        bt = entityManager.find( EntityForConverters.class, 10 );

        transaction.commit();
        assertNotNull( bt );

        entityManager.close();
    }

    @Test
    public void testUserType()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityForConverters bt = new EntityForConverters();
        bt.setField( 11 );
        bt.setMyCustomType( new MyCustomType( "lalka" ) );
        bt.setAmountUSD( new MonetaryAmount( BigDecimal.ONE, Currency.getInstance( "USD" ) ) );
        bt.setAmountEUR( new MonetaryAmount( BigDecimal.TEN, Currency.getInstance( "EUR" ) ) );
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( bt );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        bt = entityManager.createQuery( "SELECT efc FROM EntityForConverters efc where efc.amountEUR.value = '20.00'",
            EntityForConverters.class ).getSingleResult();

        bt.setMyCustomType( new MyCustomType( "not lalka" ) );
        entityManager.merge( bt );

        transaction.commit();
        assertNotNull( bt );

        entityManager.close();
    }
}
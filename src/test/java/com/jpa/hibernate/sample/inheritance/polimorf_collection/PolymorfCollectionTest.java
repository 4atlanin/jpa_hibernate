package com.jpa.hibernate.sample.inheritance.polimorf_collection;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class PolymorfCollectionTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testPolymorphCollection()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User_ user = new User_();
        // user.setId( 1 );
        user.setName( "lalka user" );
        CreditCard_ cc = getCreditCard();
        BankAccount_ bb = getBankAccount();
        user.getDefaultBilling().add( cc );
        user.getDefaultBilling().add( bb );
        cc.setUser( user );
        bb.setUser( user );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( user );
        entityManager.persist( cc );
        entityManager.persist( bb );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        User_ user2 = entityManager.find( User_.class, user.getId() );
        transaction.commit();

        Set<BillingDetail_> bd = user2.getDefaultBilling(); // тут будут нормальные объекты, не прокси
        bd.forEach( System.out::println );

        entityManager.close();
    }

    private CreditCard_ getCreditCard()
    {
        CreditCard_ cc = new CreditCard_();
        //  cc.setId( 2 );
        cc.setAccount( "1235346457" );
        cc.setExpMonth( "11" );
        cc.setExpYear( "2020" );
        cc.setOwner( "owner vasia" );
        return cc;
    }

    private BankAccount_ getBankAccount()
    {
        BankAccount_ cc = new BankAccount_();
        cc.setOwner( "owner" );
        cc.setAccount( "1235346457" );
        cc.setName( "11" );
        cc.setSwift( "2020" );
        return cc;
    }

}

package com.jpa.hibernate.sample.inheritance.polymorf_association;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.inheritance.polimorf_assocaiation.BillingDetail;
import com.jpa.hibernate.sample.inheritance.polimorf_assocaiation.CreditCard;
import com.jpa.hibernate.sample.inheritance.polimorf_assocaiation.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class PolymorfAssociationTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testOptimisticLock()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = new User();
        // user.setId( 1 );
        user.setName( "lalka user" );
        CreditCard cc = getCreditCard();
        user.setDefaultBilling( cc );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( cc );
        entityManager.persist( user );
        transaction.commit();

        entityManager.clear();

        transaction.begin();
        User user2 = entityManager.find( User.class, user.getId() );
        transaction.commit();

        BillingDetail bd = user2.getDefaultBilling();   // тут будет хибернейтовская прокся из-за FetchType.LAZY
        assertFalse( bd instanceof CreditCard );        // bd не является CreditCard, оно HybernateProxy
        //CreditCard cc = (CreditCard) bd; так что кастование в нужный тип невозможно, ClassCastException будет

        // Чтобы получить нормальную ссылку на CreditCard c Lazy загрузкой юзай getReference()
        CreditCard credC = entityManager.getReference( CreditCard.class, bd.getId() );
        assertFalse( Objects.equals( credC, bd ) );
        assertTrue( credC instanceof CreditCard );

        entityManager.close();
    }

    private CreditCard getCreditCard()
    {
        CreditCard cc = new CreditCard();
        //  cc.setId( 2 );
        cc.setAccount( "1235346457" );
        cc.setExpMonth( "11" );
        cc.setExpYear( "2020" );
        cc.setOwner( "owner vasia" );
        return cc;
    }

}

package com.jpa.hibernate.sample.relationship.bidirectional.one_to_many;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_many.ManyToOneBag;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_many.OneToManyBag;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;

@Sql( scripts = "classpath:sql/cleanup.sql" )
public class OneToManyBagTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void otmWithBagTable()
    {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        final ManyToOneBag oneManySide = getNextManyBagSide();
        final ManyToOneBag twoManySide = getNextManyBagSide();

        transaction.begin();

        OneToManyBag one = new OneToManyBag();
        entityManager.persist( one );

        oneManySide.setOneToManyBag( one );
        twoManySide.setOneToManyBag( one );

        one.setBag( Arrays.asList( oneManySide, twoManySide ) );

        entityManager.persist( oneManySide );
        entityManager.persist( twoManySide );

        transaction.commit();

        entityManager.clear();
        transaction.begin();

        OneToManyBag newOne = entityManager.find( OneToManyBag.class, one.getId() );

        ManyToOneBag three = getNextManyBagSide();
        three.setOneToManyBag( newOne );
        newOne.getBag().add( three );    //при добавлении нового объекта, тут не будет SELECT для коллекции, т.к. используется Bag 
        transaction.commit();
        entityManager.close();
    }

    private ManyToOneBag getNextManyBagSide()
    {
        ManyToOneBag entity = new ManyToOneBag();

        entity.setPayload( RandomString.make( 10 ) );
        return entity;
    }

}

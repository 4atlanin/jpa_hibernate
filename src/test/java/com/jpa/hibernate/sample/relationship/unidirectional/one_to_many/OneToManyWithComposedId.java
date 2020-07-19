/*
package com.jpa.hibernate.sample.relationship.unidirectional.one_to_many;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_many.ItemMetadata;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OneToManyWithComposedId extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testOrderOfPersistence()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.persist( one );
        manager.persist( two );
        manager.persist( three );
        transaction.commit();

        transaction.begin();

        //CASE WHEN THEN - когда значение поля равно чему-то, то взять значение из одной колонки, иначе взять значение из другой колонки. Хз есть ли аналог в SQL.
        //Это просто аналог SQL оператора
        List<String> stringLists = manager.createQuery( "SELECT CASE e.payload WHEN 'payload' THEN e.payload ELSE e.payload END FROM EntityOTOUDTwo e" )
                                          .getResultList();
        transaction.commit();

        assertEquals( 2, stringLists.size() - stringLists.stream().filter( it -> it.equals( "payload" ) ).collect( Collectors.toList() ).size() );
        manager.close();
    }

}
*/

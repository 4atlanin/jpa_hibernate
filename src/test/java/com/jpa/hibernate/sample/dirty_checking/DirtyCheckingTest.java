package com.jpa.hibernate.sample.dirty_checking;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDManySide;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many.OTMUDManySideRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many.OTMUDOneSideRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class DirtyCheckingTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void simpleJoinTableTest()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        final EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        OTMUDManySide manyFirst = getNextManySide();
        entityManager.persist( manyFirst );

        OTMUDManySide manySecond = getNextManySide();
        entityManager.persist( manySecond );

        DirtyChecking dirtyChecking = new DirtyChecking();
        dirtyChecking.setMany( Arrays.asList( manyFirst, manySecond ) );
        entityManager.flush();
        entityManager.persist( dirtyChecking );
        entityManager.flush();

        dirtyChecking.getMany();     //тут произойдёт удаление 1 из записей, из-за dirtyChecking при доступе к полям через методы
        transaction.commit();

        dirtyChecking.getMany();
    }

    private OTMUDManySide getNextManySide()
    {
        OTMUDManySide entity = new OTMUDManySide();

        entity.setPayload( RandomString.make( 10 ) );
        return entity;
    }
}
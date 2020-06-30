package com.jpa.hibernate.sample.lock;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OptimisticLockTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    //Из этого энтити мэнеджера нельзя создавать ручные транзакции, т.к. для этого мэнеджера все транзакции управляются спрингом
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testOptimisticLock()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OptimisticLockE two = new OptimisticLockE();
        two.setValue( "lalka" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        transaction.begin();
        two = entityManager.find( OptimisticLockE.class, two.getId(), LockModeType.OPTIMISTIC );
        //  two.setValue( "154" );
        transaction.commit();

        transaction.begin();

        two.setValue( "qwe" );
        two.setVersion( 0 );  //несмотря на декремент версси, всё будет ок. Эта версия будет проигнорированна, возьмётся нужная
        transaction.commit();
        assertEquals( two.getVersion(), 1 );
        entityManager.close();
    }

    @Test
    public void testOptimisticLockForceIncrement()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OptimisticLockE two = new OptimisticLockE();
        two.setValue( "lalka" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        transaction.begin();
        two = entityManager.find( OptimisticLockE.class, two.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT );  //force инкрементнет версию даже при простом чтении. При записи будет 2 инкремента
        //  two.setValue( "154" );
        transaction.commit();

        transaction.begin();
        two.setValue( "qwe" );
        transaction.commit();
        assertEquals( two.getVersion(), 2 );
        entityManager.close();
    }

    @Test
    public void testNativeQuery()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OptimisticLockE two = new OptimisticLockE();
        two.setValue( "lalka" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        transaction.begin();
        two.setValue( "154" );
        transaction.commit();

        transaction.begin();
        two = entityManager.find( OptimisticLockE.class, two.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT );
        final int id = two.getId();

        transactionTemplate.execute( ( s ) -> {
            em.find( OptimisticLockE.class, id ).setValue( "pekush" );
            return null;
        } );

        assertThrows(
            RollbackException.class,  // за RollbackException скрыто OptimisticLockException
            transaction::commit );
        entityManager.close();
    }
}

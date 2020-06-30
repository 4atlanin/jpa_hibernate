package com.jpa.hibernate.sample.entity_manager;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity_manager.orphan_removal.OrphanOne;
import com.jpa.hibernate.sample.entity_manager.orphan_removal.OrphanTwo;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.*;

import static org.junit.Assert.*;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class EntityManagerTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testOrderOfPersistence()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Несмотря на то, что в книге "Изучаем Java EE c. 233" пишут о том, что порядок персистов не важен,
        // т.к. сохранение в бд будет при коммите транзакции и entityManager сам выберет нужный порядок сохранения,
        // В ХИБЕРНЕЙТЕ ПРАВИЛЬНЫЙ ПОРЯДОК ОБЯЗАТЕЛЕН.
        entityManager.persist( two );
        entityManager.persist( one );

        transaction.commit();
        entityManager.close();
    }

    //Смысл такой же как и в предыдущем, но тут сделано по другому
    @Test
    public void testOrderOfPersistenceTheSameAsPrevious()
    {
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );

        // Несмотря на то, что в книге "Изучаем Java EE c. 233" пишут о том, что порядок персистов не важен,
        // т.к. сохранение в бд будет при коммите транзакции и entityManager сам выберет нужный порядок сохранения,
        // В ХИБЕРНЕЙТЕ ПРАВИЛЬНЫЙ ПОРЯДОК ОБЯЗАТЕЛЕН.
        transactionTemplate.execute( status -> {
            em.persist( two );
            em.persist( one );
            return null;
        } );
       // assertNotEquals( 0, two.getId() ); хз зачем эти асерты тут были
       // assertNotEquals( 0, one.getId() );

        //find венёт null если не найдёт запись в бд.
        transactionTemplate.execute( status -> {
            assertNotNull( em.find( EntityOTOUDTwo.class, two.getId() ) );
            assertNotNull( em.find( EntityOTOUDOne.class, one.getId() ) );
            return null;
        } );

        transactionTemplate.execute( status -> {
            EntityOTOUDOne lali2;
            assertNotNull( em.getReference( EntityOTOUDTwo.class, two.getId() ) );
            assertNotNull( em.getReference( EntityOTOUDOne.class, one.getId() ) );
            assertNotNull( lali2 = em.getReference( EntityOTOUDOne.class, 12345 ) );
            assertEquals( 12345, lali2.getId() );
            // Если раскоментить то упадёт EntityNotFoundException, из-за получения доступа к полям несуществующей энтити.
            // Причём если мы попытаемся получить доступ к id, то всё будет ОК,
            // lali2.getId() вернёт 12354
            //
            // System.out.println( "ALARM !!!!!!!!!!!!!!!!!  - " + lali2.getTwo() );
            //
            return null;
        } );
    }

    @Test
    public void testDelete()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist( two );
        entityManager.persist( one );

        transaction.commit();

        transaction.begin();

        entityManager.remove( one );

        // после remove энтитя удаляется из персистенс контекста
        assertFalse( entityManager.contains( one ) );
        assertNotNull( one );
        assertNull( entityManager.find( EntityOTOUDOne.class, one.getId() ) );

        transaction.commit();
        assertNotNull( one );
        entityManager.close();
    }

    @Test
    public void testDeleteWithOrphanRemoval()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrphanTwo two = new OrphanTwo();
        two.setPayload( "payload" );

        OrphanOne one = new OrphanOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist( two );
        entityManager.persist( one );

        transaction.commit();

        // one лежит в персистенс контексте
        assertTrue( entityManager.contains( one ) );
        one.setTwo( null );

        //тут никаких изменений не произойдёт, т.к. транзакция не относится к EntityManager управляющему нашими обьектами.
        transactionTemplate.execute( status -> {
            return null;
        } );

        // А тут транзакция относится к EntityManager управляющему нашими объектами, и даже если изменения с энтитями(апдейт полей)
        // произошли вне транзакции, эти изменения применяются на базу при закрытии первой следующей транзакции.
        //А вот если энтитю перед этим отсоеденить от контекста, то изменения не попадут в бд
        transaction.begin();
        transaction.commit();

        assertTrue( entityManager.contains( one ) );
        assertNotNull( one );
        assertNotNull( two );
        entityManager.close();
    }

    @Test
    public void testFlush()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrphanTwo two = new OrphanTwo();
        two.setPayload( "payload" );

        OrphanOne one = new OrphanOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //несмотря на то, что все изменения сливаются в бд при коммите транзакции, id для энтитей генерируются и инкрементятся тут.
        entityManager.persist( two );
        entityManager.persist( one );

        //Мэнеджер не будет дожидаться окончания транзакции и зальёт все изменнения которые висят в контексте.
        entityManager.flush();

        transaction.commit();

        transactionTemplate.execute( status -> {
            assertNotNull( em.find( OrphanTwo.class, two.getId() ) );
            assertNotNull( em.find( OrphanOne.class, one.getId() ) );
            return null;
        } );

        entityManager.close();
    }

    @Test
    public void testRefresh()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrphanTwo two = new OrphanTwo();
        two.setPayload( "payload" );

        OrphanOne one = new OrphanOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist( two );
        entityManager.persist( one );

        transaction.commit();

        one.setTwo( null );
        two.setPayload( "new payload" );
        entityManager.refresh( one );
        entityManager.refresh( two );

        assertNotNull( one.getTwo() );
        assertEquals( "payload", two.getPayload() );

        entityManager.close();
    }

    @Test
    public void testDetach()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrphanTwo two = new OrphanTwo();
        two.setPayload( "payload" );

        OrphanOne one = new OrphanOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist( two );
        entityManager.persist( one );

        //Если сделать detach внутри транзакции, для заперстистаных но не закоммиченных энтитей то с бд никаких изменений не будет,
        // т.к. нечего в итоге персистить.
        entityManager.detach( one );
        entityManager.detach( two );

        //тут уже бесмысленно, но в общем clear убирает все энтити из контекста, detach только конкретную
        entityManager.clear();

        assertFalse( entityManager.contains( one ) );
        transaction.commit();

        transactionTemplate.execute( status -> {
            assertNull( em.find( OrphanTwo.class, two.getId() ) );
            assertNull( em.find( OrphanOne.class, one.getId() ) );
            return null;
        } );

        assertNotNull( one.getTwo() );
        assertEquals( "payload", two.getPayload() );

        entityManager.close();
    }

    @Test
    public void testMerge()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        OrphanTwo two = new OrphanTwo();
        two.setPayload( "payload" );

        OrphanOne one = new OrphanOne();
        one.setTwo( two );
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        entityManager.persist( one );
        transaction.commit();

        entityManager.clear();

        two.setPayload( "new payload" );

        assertFalse( entityManager.contains( two ) );
        transaction.begin();
        //короч, merge придетачивает отсоеденённую но, существующую в бд энтитю, к контексту. естественно со всеми изменениями
        entityManager.merge( two );

        //two после мерджа всё ещё не присоеденена к контексту!!!
        assertFalse( entityManager.contains( two ) );
        //
        two.setPayload( "payload After Merge" );
        transaction.commit();

        //two после коммита транзакции всё ещё не присоеденена к контексту!!!
        assertEquals( "payload After Merge", two.getPayload() );

        transaction.begin();
        transaction.commit();

        //two после коммита второй всё ещё не присоеденена к контексту!!!
        assertFalse( entityManager.contains( two ) );

        transactionTemplate.execute( status -> {
            //достанет со значением newPayload
            assertEquals( "new payload", entityManager.find( OrphanTwo.class, two.getId() ).getPayload() );
            return null;
        } );

    }

    //todo До конца не понял, но вот что понял
    // 1. такое происходит при       ddl-auto: create-drop  + внутри транзакции сделали флаш изменений и потом бросили исключение
    // create-drop - create and drop the schema automatically when a session is starts and ends
    // В БД такое: Waiting for table metadata lock	drop table if exists just_test_entity
    // дроп тэйбл - это занчит начала работать create-drop  пропертя
    // вот что в доке по нему - Drop the schema and recreate it on SessionFactory startup. Additionally, drop the schema on SessionFactory shutdown.
    // т.е. получается, что SessionFactory на исключении закрылся, create-drop начал удалять схему в бд, а транзакция не закончилась и держит записи в бд (!!! Тут может быть не точно)
    // Если перед исключением сделать rollback, тогда всё ок.
    @Test
    @Disabled
    public void testRollbackAfterFlush()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        OrphanTwo one = new OrphanTwo();

        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();

        entityManager.persist( one );
        entityManager.flush();

        if( entityManager != null )
        {
           // transaction.rollback();
            throw new RuntimeException( "some exception" );
        }
        transaction.commit();

    }
}
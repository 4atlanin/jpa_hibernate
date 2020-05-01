package com.jpa.hibernate.sample.jpql;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.attributes.DTOFromJPQL;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

import static com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class JPQLTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    //Из этого энтити мэнеджера нельзя создавать ркчные транзакции, т.к. для этого мэнеджера все транзакции управляются спрингом
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testOrderOfPersistence()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo one = getEntity();
        one.setPayload( "payload" );

        EntityOTOUDTwo two = getEntity();

        EntityOTOUDTwo three = getEntity();

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
    }

    private EntityOTOUDTwo getEntity()
    {
        EntityOTOUDTwo entity = new EntityOTOUDTwo();
        entity.setPayload( RandomStringUtils.randomAlphanumeric( 10 ) );
        entity.setAnotherString( RandomStringUtils.randomAlphanumeric( 10 ) );
        return entity;
    }

    @Test
    public void testNewInJPQL()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo one = getEntity();
        one.setPayload( "payload" );
        EntityOTOUDTwo two = getEntity();
        EntityOTOUDTwo three = getEntity();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.persist( one );
        manager.persist( two );
        manager.persist( three );
        transaction.commit();

        transaction.begin();

        //Пример, как засетать результат JPQL запроса в обычную DTO
        List<DTOFromJPQL> stringLists = manager.createQuery( "SELECT NEW com.jpa.hibernate.sample.attributes.DTOFromJPQL(e.payload, e.anotherString) FROM EntityOTOUDTwo e" )
                                               .getResultList();
        transaction.commit();
    }

    @Test
    public void testTypedQuery()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo one = getEntity();
        one.setPayload( "payload" );
        EntityOTOUDTwo two = getEntity();
        EntityOTOUDTwo three = getEntity();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.persist( one );
        manager.persist( two );
        manager.persist( three );
        transaction.commit();

        transaction.begin();

        //позволяет создать кверю которая в результате возвращает не Object, а нужный тип
        List<EntityOTOUDTwo> stringLists = manager.createQuery( "SELECT e FROM EntityOTOUDTwo e", EntityOTOUDTwo.class )
                                                  .getResultList();
        transaction.commit();
    }

    @Test
    public void testPositionParameter()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo one = getEntity();
        one.setPayload( "payload" );
        EntityOTOUDTwo two = getEntity();
        EntityOTOUDTwo three = getEntity();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.persist( one );
        manager.persist( two );
        manager.persist( three );
        transaction.commit();

        transaction.begin();

        //позволяет создать кверю которая в результате возвращает не Object, а нужный тип
        List<EntityOTOUDTwo> stringLists = manager.createQuery( "SELECT e FROM EntityOTOUDTwo e WHERE e.payload = ?1", EntityOTOUDTwo.class )
                                                  .setParameter( 1, "payload" )
                                                  .getResultList();
        transaction.commit();
        assertFalse( stringLists.isEmpty() );
    }

    @Test
    public void testNamedParameter()
    {
        EntityManager manager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo one = getEntity();
        one.setPayload( "payload" );
        EntityOTOUDTwo two = getEntity();
        EntityOTOUDTwo three = getEntity();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.persist( one );
        manager.persist( two );
        manager.persist( three );
        transaction.commit();

        transaction.begin();

        //И это пример Динамического запроса
        //позволяет создать кверю которая в результате возвращает не Object, а нужный тип
        //!!! тут используется TypedQuery
        List<EntityOTOUDTwo> stringLists = manager.createQuery( "SELECT e FROM EntityOTOUDTwo e WHERE e.payload = :payloadName", EntityOTOUDTwo.class )
                                                  .setParameter( "payloadName", "payload" )
                                                  .getResultList();
        transaction.commit();
        assertFalse( stringLists.isEmpty() );
    }

    @Test
    public void testNamedQuery()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        transaction.begin();
        two = entityManager.createNamedQuery( FIND_BY_HARDCODED_PAYLOAD, EntityOTOUDTwo.class )
                           .getSingleResult();
        assertEquals( "payload", two.getPayload() );
        two.setPayload( "abra cadabra" );
        transaction.commit();

        transaction.begin();
        String payload = entityManager.createNamedQuery( FIND_BY_PAYLOAD, String.class )
                                      .setParameter( "payload", "abra cadabra" )
                                      .getSingleResult();

        assertEquals( "abra cadabra", payload );

        transaction.commit();
    }

    @Test
    public void testCriteriaAPI()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        // Критериа это какой-то жопа
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<EntityOTOUDTwo> query = criteriaBuilder.createQuery( EntityOTOUDTwo.class );
        final Root<EntityOTOUDTwo> root = query.from( EntityOTOUDTwo.class );
        query.select( root ).where( criteriaBuilder.equal( root.get( "payload" ), "payload" ) );

        transaction.begin();
        EntityOTOUDTwo entity = entityManager.createQuery( query ).getSingleResult();
        assertEquals( "payload", entity.getPayload() );
        transaction.commit();
    }

    @Test
    public void testNativeQuery()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( two );
        transaction.commit();

        transaction.begin();
        assertEquals( 1, entityManager.createNativeQuery( "SELECT * FROM oto_ud_two", EntityOTOUDTwo.class ).getResultList().size() );
        transaction.commit();
    }

    @Test
    public void testStoredProcedureQuery()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.createNativeQuery( "CREATE PROCEDURE simple_procedure(IN numb INT, OUT result VARCHAR(50))\n" +
                                         "BEGIN\n" +
                                         "    SET result = CONCAT('Hello world', numb);\n" +
                                         "END" ).executeUpdate();
        entityManager.flush();

        String string = (String) entityManager.createStoredProcedureQuery( "simple_procedure" )
                                              .registerStoredProcedureParameter( "numb", Integer.class, ParameterMode.IN )
                                              .registerStoredProcedureParameter( "result", String.class, ParameterMode.OUT )
                                              .setParameter( "numb", 1 )
                                              // для OUT ненужно связывание параметра со значением
                                              .getOutputParameterValue( "result" );
        transaction.commit();

        assertEquals( "Hello world1", string );




        //пример с NamedStoredProcedure
        transaction.begin();

        string = (String) entityManager.createNamedStoredProcedureQuery( STORED_PROCEDURE_NAMED_QUERY )
                                       .setParameter( "numb", 5 )
                                       .getOutputParameterValue( "result" );

        transaction.commit();

        assertEquals( "Hello world5", string );

    }

}


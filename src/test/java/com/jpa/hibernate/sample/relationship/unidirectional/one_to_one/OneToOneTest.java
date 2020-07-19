package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDTwoRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OneToOneTest extends JpaHibernateBaseTest
{
    @Autowired
    private EntityOTOUDOneRepository otoudOneRepository;

    @Autowired
    private EntityOTOUDTwoRepository otoudtwoRepository;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void unidirectionalSimple()
    {

        //нет каскадов, поэтому вручную сохраяем зависимую энтитю
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );
        otoudtwoRepository.save( two );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );

        one = otoudOneRepository.save( one );

        assertThrows( LazyInitializationException.class, otoudOneRepository.findById( one.getId() ).get().getTwo()::getPayload );

        // Если брать просто ID, то LazyInitializationException не кидается. Т.к id уже есть в энтити.
        //assertThrows( LazyInitializationException.class, otoudOneRepository.findById( one.getId() ).get().getTwo()::getId );
    }

    @Test
    public void unidirectionalPrimaryJoinColumnTest()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        AddressPkJoinColumnMaster address = new AddressPkJoinColumnMaster( "Street" );
        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( address );
        UserPkJoinColumnSlave user = new UserPkJoinColumnSlave( address.getId(), "name" );
        entityManager.persist( user );
        //     user.setAddress( address );

        transaction.commit();

        entityManager.clear();

        transaction.begin();
        final List<UserPkJoinColumnSlave> list =
            entityManager.createQuery( "SELECT u FROM UserPkJoinColumnSlave u", UserPkJoinColumnSlave.class ).getResultList();
        transaction.commit();

        entityManager.close();

    }
}
package com.jpa.hibernate.sample.relationship.bidirectional.one_to_one;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_one.AddressBiOwner;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_one.EntityOne;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_one.EntityTwo;
import com.jpa.hibernate.sample.relationship.biderectional.one_to_one.UserBiSlave;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import com.jpa.hibernate.sample.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one.EntityOTOBDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one.EntityOTOBDTwoRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDTwoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( scripts = "classpath:sql/cleanup.sql" )
public class OneToOneTest extends JpaHibernateBaseTest
{
    Random random = new Random();

    @Autowired
    private EntityOTOBDOneRepository otobdOneRepository;
    @Autowired
    private EntityOTOBDTwoRepository otobdTwoRepository;

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
        otoudtwoRepository.save( two );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );

        two = otoudtwoRepository.save( two );
        one = otoudOneRepository.save( one );

        assertNotNull( otoudOneRepository.findById( one.getId() ).get().getTwo().getId() );

    }

    /**
     * Простой пример с двунаправленной OneToOne
     */
    @Test
    public void bidirectionalTest()
    {
        EntityOne one = new EntityOne();
        one.setId( random.nextLong() );

        EntityTwo two = new EntityTwo();
        two.setId( random.nextLong() );
        two.setOne( one );
        one.setTwo( two );

        // не выставлены каскады, поэтому зависимые сущности нужно сохранить вручную.
        otobdTwoRepository.save( two );
        otobdOneRepository.save( one );

        assertNotNull( otobdTwoRepository.findById( two.getId() ).get().getOne() );
        assertNotNull( otobdOneRepository.findById( one.getId() ).get().getTwo() );
    }

    @Test
    public void bidirectionalPrimaryJoinColumnTest()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserBiSlave user = new UserBiSlave( "name" );
        AddressBiOwner address = new AddressBiOwner( "Street", user );
        user.setAddress( address );

        final EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist( user );
        //     user.setAddress( address );

        transaction.commit();

        entityManager.clear();

        transaction.begin();
        final List<UserBiSlave> list =
            entityManager.createQuery( "SELECT u FROM UserBiSlave u", UserBiSlave.class ).getResultList();
        transaction.commit();

        entityManager.close();

    }
}

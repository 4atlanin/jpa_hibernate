package com.jpa.hibernate.sample.batch;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class BatchTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void testBatch()
    {
        ItemBatch onei = createItem();
        ItemBatch twoi = createItem();
        ItemBatch threei = createItem();

        BidBatch oneb = createBid();
        BidBatch twob = createBid();
        BidBatch threeb = createBid();
        BidBatch fourb = createBid();
        BidBatch fiveb = createBid();
        BidBatch sixb = createBid();
        BidBatch sevenb = createBid();
        BidBatch eightb = createBid();
        BidBatch nineb = createBid();
        BidBatch ten = createBid();
        BidBatch eleven = createBid();

        onei.setBids( oneb );
        onei.setBids( twob );
        onei.setBids( threeb );

        twoi.setBids( fourb );
        twoi.setBids( fiveb );
        twoi.setBids( sixb );

        threei.setBids( sevenb );
        threei.setBids( eightb );
        threei.setBids( nineb );
        threei.setBids( ten );
        threei.setBids( eleven );

        EntityManager em = entityManagerFactory.createEntityManager();

        final EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist( onei );
        em.persist( twoi );
        em.persist( threei );

        transaction.commit();

        em.clear();

        transaction.begin();
        final List<ItemBatch> itemBatches = em.createQuery( "SELECT i FROM ItemBatch i" ).getResultList();

        for( ItemBatch ib : itemBatches )
        {
            assertTrue( ib.bids.size() > 0 );
        }

        transaction.commit();

        em.close();
    }

    public ItemBatch createItem()
    {
        return new ItemBatch( RandomStringUtils.randomAlphanumeric( 5 ) );
    }

    public BidBatch createBid()
    {
        return new BidBatch( RandomStringUtils.randomAlphanumeric( 5 ) );
    }
}
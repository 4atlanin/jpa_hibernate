package com.jpa.hibernate.sample.relationship.one_to_one;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.relationship.one_to_one.bidirectional.EntityOne;
import com.jpa.hibernate.sample.entity.relationship.one_to_one.bidirectional.EntityTwo;
import com.jpa.hibernate.sample.repository.relationship.one_to_one.EntityOneRepository;
import com.jpa.hibernate.sample.repository.relationship.one_to_one.EntityTwoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OneToOneTest extends JpaHibernateBaseTest
{
    Random random = new Random();

    @Autowired
    private EntityOneRepository oneRepository;

    @Autowired
    private EntityTwoRepository twoRepository;

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
        twoRepository.save( two );
        oneRepository.save( one );

        assertNotNull( twoRepository.findById( two.getId() ).get().getOne() );
        assertNotNull( oneRepository.findById( one.getId() ).get().getTwo() );
    }
}

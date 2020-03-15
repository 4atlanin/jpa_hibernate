package com.jpa.hibernate.sample.relationship.bidirectional.one_to_one;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one.EntityOne;
import com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one.EntityTwo;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one.EntityOTOBDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one.EntityOTOBDTwoRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.EntityOTOUDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.EntityOTOUDTwoRepository;
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
    private EntityOTOBDOneRepository otobdOneRepository;
    @Autowired
    private EntityOTOBDTwoRepository otobdTwoRepository;

    @Autowired
    private EntityOTOUDOneRepository otoudOneRepository;

    @Autowired
    private EntityOTOUDTwoRepository otoudtwoRepository;

    @Test
    public void unidirectionalSimple()
    {

        //нет каскадов, поэтому вручную сохраяем зависимую энтитю
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        otoudtwoRepository.save( two );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );

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
}

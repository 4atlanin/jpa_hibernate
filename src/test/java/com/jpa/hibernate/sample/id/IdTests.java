package com.jpa.hibernate.sample.id;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.table.class_id.IdClassSample;
import com.jpa.hibernate.sample.entity.table.embeded_id.EmbeddedIdSample;
import com.jpa.hibernate.sample.entity.table.embeded_id.MyEmbeddedId;
import com.jpa.hibernate.sample.repository.table.EmbeddedIdRepository;
import com.jpa.hibernate.sample.repository.table.IdClassSampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class IdTests extends JpaHibernateBaseTest {
    @Autowired
    private EmbeddedIdRepository embeddedIdRepository;

    @Autowired
    private IdClassSampleRepository idClassSampleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Random random = new Random();

    @Test
    public void testEmbeddedId() {
        EmbeddedIdSample eis = getEmbeddedIdSample();
        EmbeddedIdSample eis2 = getEmbeddedIdSample();
        embeddedIdRepository.save(eis);
        embeddedIdRepository.save(eis2);
        embeddedIdRepository.findAll().forEach( entity -> {
            boolean first = entity.getId().equals( eis.getId() );
            boolean second = entity.getId().equals( eis2.getId() );
            assertTrue( first || second ); // проверка, что есть совпадения
            assertFalse( first && second ); //проверка что есть только 1 совпадение
        } );
    }

    private EmbeddedIdSample getEmbeddedIdSample() {
        EmbeddedIdSample eis = new EmbeddedIdSample();
        MyEmbeddedId mei = new MyEmbeddedId();
        mei.setPartOne(random.nextInt());
        mei.setPartTwo(random.nextInt());
        eis.setId(mei);

        return eis;
    }

    @Test
    public void testClassIdSample()
    {
        IdClassSample ics = getIdClassSample();
        IdClassSample ics2 = getIdClassSample();
        idClassSampleRepository.save( ics );
        idClassSampleRepository.save( ics2 );
        assertNotNull( idClassSampleRepository.findById( ics.getId() ).orElseGet( null ) );
        assertNotNull( idClassSampleRepository.findById( ics2.getId() ).orElseGet( null ) );

    }

    private IdClassSample getIdClassSample()
    {
        IdClassSample idcs = new IdClassSample();
        idcs.setPartOne( random.nextInt() );
        idcs.setPartTwo( random.nextInt() );
        return idcs;
    }

    //показывает как юзаются в квери разные типы ID
    @Test
    public void showDifferenceJPQLEmbeddedIdClass()
    {
        IdClassSample ics = getIdClassSample();
        IdClassSample ics2 = getIdClassSample();
        idClassSampleRepository.save( ics );
        idClassSampleRepository.save( ics2 );

        EmbeddedIdSample eis = getEmbeddedIdSample();
        EmbeddedIdSample eis2 = getEmbeddedIdSample();
        embeddedIdRepository.save( eis );
        embeddedIdRepository.save( eis2 );

        List idClassList = entityManager.createQuery( "SELECT ics.partOne FROM IdClassSample ics" ).getResultList();
        List embeddedIdList = entityManager.createQuery( "SELECT eis.id.partOne FROM EmbeddedIdSample eis" ).getResultList();
        assertEquals( 2, embeddedIdList.size() );
        assertEquals( 2, idClassList.size() );
    }
}

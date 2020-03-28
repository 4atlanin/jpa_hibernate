package com.jpa.hibernate.sample.table;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.table.embedded.entity.EmbeddableRepository;
import com.jpa.hibernate.sample.table.embedded.entity.EmbeddedEntity;
import com.jpa.hibernate.sample.table.embedded.entity.EmbeddedEntityGoDeeper;
import com.jpa.hibernate.sample.table.embedded.entity.EmbeddedEntityOwner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Random;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class EmbeddedEntityTest extends JpaHibernateBaseTest
{
    @Autowired
    private EmbeddableRepository repository;
    private Random random = new Random();

    @Test
    public void test()
    {
        int id = random.nextInt();

        EmbeddedEntityGoDeeper eegd = new EmbeddedEntityGoDeeper();
        eegd.setDeepField( "deeper" );

        EmbeddedEntity ee = new EmbeddedEntity();
        ee.setGoDeeper( eegd );
        ee.setFieldFromEmbeddedEntity( "middleEntity" );

        EmbeddedEntityOwner eeo = new EmbeddedEntityOwner();
        eeo.setId( id );
        eeo.setEmbeddedEntity( ee );

        // сохранит всё в одну таблицу
        repository.save( eeo );

    }

}

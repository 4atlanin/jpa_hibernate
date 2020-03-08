package com.jpa.hibernate.sample.table;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.table.collections.ElementCollectionEntity;
import com.jpa.hibernate.sample.entity.table.collections.ElementCollectionRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class ElementCollectionTest extends JpaHibernateBaseTest
{
    @Autowired
    private ElementCollectionRepository repository;
    private Random random = new Random();

    /*
    * Создаст доп таблицу в которой каждоя строка будет хранить id владеющей сущности и 1 значение из коллекции.
    */
    @Test
    public void simpleCollectionTest() throws InterruptedException
    {
        ElementCollectionEntity entity = new ElementCollectionEntity();
        entity.setId( random.nextInt() );
        entity.setInfo( Arrays.asList( "ONE", "TWO" ) );
        repository.save( entity );

    }

    @Test
    public void testLazyLoadOfCollectionTest() throws InterruptedException
    {
        int id = random.nextInt();

        ElementCollectionEntity entity = new ElementCollectionEntity();
        entity.setId( id );
        entity.setInfo( Arrays.asList( "ONE", "TWO" ) );
        repository.save( entity );


        ElementCollectionEntity retrievedEntity = repository.getOne( id );

        //Вылетит LazyInitializationException т.к.
        assertThrows( LazyInitializationException.class, retrievedEntity::getInfo );

    }
}

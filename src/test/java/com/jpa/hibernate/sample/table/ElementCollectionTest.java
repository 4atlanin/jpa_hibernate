package com.jpa.hibernate.sample.table;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.table.collections.ElementCollectionEntity;
import com.jpa.hibernate.sample.table.collections.ElementCollectionRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void simpleListCollectionTest()
    {
        ElementCollectionEntity entity = new ElementCollectionEntity();
        entity.setId( random.nextInt() );
        entity.setList( Arrays.asList( "ONE", "TWO" ) );
        repository.save( entity );

    }

    @Test
    public void testLazyLoadOfListCollectionTest()
    {
        int id = random.nextInt();

        ElementCollectionEntity entity = new ElementCollectionEntity();
        entity.setId( id );
        entity.setList( Arrays.asList( "ONE", "TWO" ) );
        repository.save( entity );

        ElementCollectionEntity retrievedEntity = repository.getOne( id );

        //Вылетит LazyInitializationException т.к.
        assertThrows( LazyInitializationException.class, retrievedEntity::getList );
    }

    @Test
    public void testSimpleMapCollectionTest()
    {
        Map<String, String> map = new HashMap<>();
        map.put( "ONE", "VALUE_ONE" );
        map.put( "TWO", "VALUE_TWO" );
        ElementCollectionEntity entity = new ElementCollectionEntity();

        entity.setId( random.nextInt() );
        entity.setMap( map );
        repository.save( entity );
    }

    @Test
    public void testLazyLoadOfMapCollectionTest()
    {
        int id = random.nextInt();

        Map<String, String> map = new HashMap<>();
        map.put( "ONE", "VALUE_ONE" );
        map.put( "TWO", "VALUE_TWO" );
        ElementCollectionEntity entity = new ElementCollectionEntity();

        entity.setId( id );
        entity.setMap( map );
        repository.save( entity );

        ElementCollectionEntity retrievedEntity = repository.getOne( id );

        //Вылетит LazyInitializationException т.к.
        assertThrows( LazyInitializationException.class, retrievedEntity::getMap );
    }

}
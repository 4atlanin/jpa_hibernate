package com.jpa.hibernate.sample.inheritance.single_table;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class SingleTableTest extends JpaHibernateBaseTest
{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CDRepository cdRepository;

    //just to look at the tables
    @Test
    public void testInheritanceWithSingleTable()
    {
        Book book = bookRepository.save( getBook() );
        Item item = itemRepository.save( getItem() );
        CD cd = cdRepository.save( getCD() );

        assertTrue( true );

    }

    private Book getBook()
    {
        Book book = new Book();
        book.setTitle( "Book title" );
        book.setDescription( "book description" );
        book.setPrice( 100 );
        return book;
    }

    private Item getItem()
    {
        Item item = new Item();
        item.setDescription( "item description" );
        item.setPrice( 100 );
        return item;
    }

    private CD getCD()
    {
        CD cd = new CD();
        cd.setBand( "Band" );
        cd.setNumberOfRecords( 10 );
        cd.setDescription( "cd description" );
        cd.setPrice( 100 );
        return cd;
    }
}

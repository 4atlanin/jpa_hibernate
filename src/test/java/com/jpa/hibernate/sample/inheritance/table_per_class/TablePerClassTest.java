package com.jpa.hibernate.sample.inheritance.table_per_class;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class TablePerClassTest extends JpaHibernateBaseTest
{
    @Autowired
    private TPCBookRepository bookRepository;
    @Autowired
    private TPCItemRepository itemRepository;
    @Autowired
    private TPCNewsPaperRepository newsPaperRepository;

    //just to look at the tables
    @Test
    public void testInheritanceWithSingleTable()
    {
        TPCBook book = bookRepository.save( getBook() );
        TPCItem item = itemRepository.save( getItem() );
        TPCNewsPaper cd = newsPaperRepository.save( getNewspaper() );

        assertTrue( true );
    }

    private TPCBook getBook()
    {
        TPCBook book = new TPCBook();
        book.setTitle( "Book title" );
        book.setDescription( "book description" );
        book.setPrice( 100 );
        return book;
    }

    private TPCItem getItem()
    {
        TPCItem item = new TPCItem();
        item.setDescription( "item description" );
        item.setPrice( 100 );
        return item;
    }

    private TPCNewsPaper getNewspaper()
    {
        TPCNewsPaper newsPaper = new TPCNewsPaper();
        newsPaper.setPages( 10 );
        newsPaper.setDescription( "cd description" );
        newsPaper.setPrice( 100 );
        return newsPaper;
    }
}

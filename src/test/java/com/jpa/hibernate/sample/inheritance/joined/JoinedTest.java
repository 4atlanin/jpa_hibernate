package com.jpa.hibernate.sample.inheritance.joined;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class JoinedTest extends JpaHibernateBaseTest
{
    @Autowired
    private JoinedBookRepository joinedBookRepository;
    @Autowired
    private JoinedItemRepository itemRepository;
    @Autowired
    private NewsPaperRepository newsPaperRepository;

    //just to look at the tables
    @Test
    public void testInheritanceWithSingleTable()
    {
        JoinedBook book = joinedBookRepository.save( getBook() );
        JoinedItem item = itemRepository.save( getItem() );
        NewsPaper cd = newsPaperRepository.save( getNewspaper() );

        assertTrue( true );
    }

    private JoinedBook getBook()
    {
        JoinedBook book = new JoinedBook();
        book.setTitle( "Book title" );
        book.setDescription( "book description" );
        book.setPrice( 100 );
        return book;
    }

    private JoinedItem getItem()
    {
        JoinedItem item = new JoinedItem();
        item.setDescription( "item description" );
        item.setPrice( 100 );
        return item;
    }

    private NewsPaper getNewspaper()
    {
        NewsPaper newsPaper = new NewsPaper();
        newsPaper.setPages( 10 );
        newsPaper.setDescription( "cd description" );
        newsPaper.setPrice( 100 );
        return newsPaper;
    }
}

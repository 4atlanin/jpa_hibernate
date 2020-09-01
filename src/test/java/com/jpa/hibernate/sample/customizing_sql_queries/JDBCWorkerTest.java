package com.jpa.hibernate.sample.customizing_sql_queries;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.configuration.QueryItemWork;
import com.jpa.hibernate.sample.inheritance.polimorf_collection.User_;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.*;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class JDBCWorkerTest extends JpaHibernateBaseTest
{
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void workerTest()
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        User_ user = getUser_();
        em.persist( user );
        em.flush();   // сброс изменений вручную
        final Session session = em.unwrap( Session.class );
        
        session.doWork( new QueryItemWork( user.getId() ) );  // эта строчка не вызовет сброса изменений в БД
        
        transaction.commit();
        em.close();
    }

    private User_ getUser_()
    {
        User_ user = new User_();
        user.setName( "lalka user" );
        
        return user;
    }
}

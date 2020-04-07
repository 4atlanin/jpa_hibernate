package com.jpa.hibernate.sample.inheritance.mapped_super_class;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class MappedSuperClassTest extends JpaHibernateBaseTest
{
    @Autowired
    private MappedClassRepository repository;

    @Test
    public void mappedSuperClassTest()
    {
        ExtenderClass extenderClass = new ExtenderClass();
        extenderClass.setOuterField( "outerField" );
        extenderClass.setColumnToOverride( "collumnToOverride" );
        extenderClass.setMappedColumn( "mappedColumn" );

        extenderClass = repository.save( extenderClass );

        assertNotNull( extenderClass );
    }
}

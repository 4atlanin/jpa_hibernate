package com.jpa.hibernate.sample.table;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.repository.table.SecondaryTableSampleRepository;
import com.jpa.hibernate.sample.repository.table.TableSampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class TableTest extends JpaHibernateBaseTest
{
    @Autowired
    private TableSampleRepository tableSampleRepository;

    @Autowired
    private SecondaryTableSampleRepository secondaryTableSampleRepository;

    @Test
    void entityManagerSample()
    {
        TableSample ts = new TableSample();
        ts.setField( "field" );
        tableSampleRepository.saveTableSample( ts );

        assertEquals( 1, tableSampleRepository.getAllTableSample(  ).size() );
        assertEquals( 1, tableSampleRepository.getTableSamplesNamedQuery().size() );
    }

    @Test
    void secondaryTableTest()
    {
        SecondaryTableSample sts = new SecondaryTableSample();
        sts.setCity( "Brest" );
        sts.setCountry( "France" );
        sts.setStreet( "Cagoule" );
        SecondaryTableSample stsSaved = secondaryTableSampleRepository.save( sts );
        assertNotNull( stsSaved );
    }
}

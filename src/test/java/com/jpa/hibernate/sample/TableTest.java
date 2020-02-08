package com.jpa.hibernate.sample;

import com.jpa.hibernate.sample.entity.table.TableSample;
import com.jpa.hibernate.sample.repository.table.TableSampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableTest extends JpaHibernateBaseTest
{
    @Autowired
    private TableSampleRepository tableSampleRepository;

    @Test
    public void entityManagerSample() {
        TableSample ts = new TableSample();
        ts.setField( "field" );
        tableSampleRepository.saveTableSample( ts );

        assertEquals( 1, tableSampleRepository.getAllTableSample(  ).size() );
    }
}

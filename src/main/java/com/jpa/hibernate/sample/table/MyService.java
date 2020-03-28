package com.jpa.hibernate.sample.table;

import com.jpa.hibernate.sample.repository.table.TableSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService
{

    @Autowired
    private TableSampleRepository repository;


   // @Scheduled( fixedDelay = 10000 )
    public void scheduleFixedDelayTask()
    {
        TableSample ts = new TableSample();
        ts.setField( "field" );
        repository.saveTableSample( ts );
    }
}
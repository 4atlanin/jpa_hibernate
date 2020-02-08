package com.jpa.hibernate.sample.repository.table;

import com.jpa.hibernate.sample.entity.table.TableSample;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Data
@Repository
public class TableSampleRepository
{
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveTableSample( TableSample sample )
    {
        entityManager.persist( sample );
    }

    @Transactional
    public List<TableSample> getAllTableSample( )
    {
        List<TableSample> tableSamples = (List<TableSample>) entityManager.createNativeQuery( "select * from table_sample" ).getResultList();
        return tableSamples;
    }
}

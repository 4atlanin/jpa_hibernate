package com.jpa.hibernate.sample.repository.table;

import com.jpa.hibernate.sample.entity.table.class_id.IdClassSample;
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
        List<TableSample> tableSamples = entityManager.createNativeQuery( "select * from table_sample", TableSample.class ).getResultList();
        entityManager.createNamedQuery( "findAllTableSamplesNamedQuery", TableSample.class ).getFirstResult();
        return tableSamples;
    }

    @Transactional
    public List<TableSample> getTableSamplesNamedQuery()
    {
        List<TableSample> tableSamples = entityManager.createNamedQuery( "findAllTableSamplesNamedQuery", TableSample.class ).getResultList();
        return tableSamples;
    }
}

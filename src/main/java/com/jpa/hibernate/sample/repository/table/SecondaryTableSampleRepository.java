package com.jpa.hibernate.sample.repository.table;

import com.jpa.hibernate.sample.table.SecondaryTableSample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryTableSampleRepository extends JpaRepository<SecondaryTableSample, Integer>
{
}

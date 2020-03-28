package com.jpa.hibernate.sample.repository.table;

import com.jpa.hibernate.sample.table.class_id.ClassId;
import com.jpa.hibernate.sample.table.class_id.IdClassSample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdClassSampleRepository extends JpaRepository<IdClassSample, ClassId> {
}

package com.jpa.hibernate.sample.repository.table;

import com.jpa.hibernate.sample.entity.table.embeded_id.EmbeddedIdSample;
import com.jpa.hibernate.sample.entity.table.embeded_id.MyEmbeddedId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddedIdRepository extends JpaRepository<EmbeddedIdSample, MyEmbeddedId> {
    
}
package com.jpa.hibernate.sample.entity.table.embedded.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmbeddableRepository extends JpaRepository<EmbeddedEntityOwner, Integer>
{
}

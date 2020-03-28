package com.jpa.hibernate.sample.repository.attributes;

import com.jpa.hibernate.sample.attributes.EntityAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityAttributesRepository extends JpaRepository<EntityAttributes, Integer>
{
}

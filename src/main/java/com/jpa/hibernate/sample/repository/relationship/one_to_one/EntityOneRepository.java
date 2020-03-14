package com.jpa.hibernate.sample.repository.relationship.one_to_one;

import com.jpa.hibernate.sample.entity.relationship.one_to_one.bidirectional.EntityOne;
import org.springframework.data.repository.CrudRepository;

public interface EntityOneRepository extends CrudRepository<EntityOne, Long>
{
}

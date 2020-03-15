package com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one;

import com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one.EntityOne;
import org.springframework.data.repository.CrudRepository;

public interface EntityOTOBDOneRepository extends CrudRepository<EntityOne, Long>
{
}

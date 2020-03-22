package com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one;

import com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one.EntityOne;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import org.springframework.data.repository.CrudRepository;

public interface EntityOTOUDOneRepository extends CrudRepository<EntityOTOUDOne, Integer>
{
}

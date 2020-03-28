package com.jpa.hibernate.sample.repository.relationship.bidirectional.one_to_one;

import com.jpa.hibernate.sample.relationship.biderectional.one_to_one.EntityTwo;
import org.springframework.data.repository.CrudRepository;

public interface EntityOTOBDTwoRepository extends CrudRepository<EntityTwo, Long>
{
}

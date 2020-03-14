package com.jpa.hibernate.sample.repository.relationship.one_to_one;

import com.jpa.hibernate.sample.entity.relationship.one_to_one.bidirectional.EntityTwo;
import org.springframework.data.repository.CrudRepository;

public interface EntityTwoRepository extends CrudRepository<EntityTwo, Long>
{

}

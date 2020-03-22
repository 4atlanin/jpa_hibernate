package com.jpa.hibernate.sample.repository.relationship.bidirectional.many_to_many;

import com.jpa.hibernate.sample.entity.relationship.biderectional.many_to_many.ManyRightSide;
import com.jpa.hibernate.sample.entity.relationship.biderectional.one_to_one.EntityTwo;
import org.springframework.data.repository.CrudRepository;

public interface MTMBDRightRepository extends CrudRepository<ManyRightSide, Integer>
{
}

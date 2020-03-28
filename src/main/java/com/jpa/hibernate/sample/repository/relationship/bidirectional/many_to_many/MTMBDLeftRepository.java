package com.jpa.hibernate.sample.repository.relationship.bidirectional.many_to_many;

import com.jpa.hibernate.sample.relationship.biderectional.many_to_many.ManyLeftSide;
import org.springframework.data.repository.CrudRepository;

public interface MTMBDLeftRepository extends CrudRepository<ManyLeftSide, Integer>
{
}

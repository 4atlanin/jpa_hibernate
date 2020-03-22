package com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many;

import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_many.OTMUDOneSide;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import org.springframework.data.repository.CrudRepository;

public interface OTMUDOneSideRepository extends CrudRepository<OTMUDOneSide, Integer>
{
}

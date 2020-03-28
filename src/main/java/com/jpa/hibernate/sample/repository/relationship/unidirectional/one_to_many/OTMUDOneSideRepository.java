package com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many;

import com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDOneSide;
import org.springframework.data.repository.CrudRepository;

public interface OTMUDOneSideRepository extends CrudRepository<OTMUDOneSide, Integer>
{
}

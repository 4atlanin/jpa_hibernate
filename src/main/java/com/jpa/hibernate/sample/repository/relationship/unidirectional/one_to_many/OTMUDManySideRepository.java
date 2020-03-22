package com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many;

import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_many.OTMUDManySide;
import org.springframework.data.repository.CrudRepository;

public interface OTMUDManySideRepository extends CrudRepository<OTMUDManySide, Integer>
{
}

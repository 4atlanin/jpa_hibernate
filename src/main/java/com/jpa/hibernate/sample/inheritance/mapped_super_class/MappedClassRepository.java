package com.jpa.hibernate.sample.inheritance.mapped_super_class;

import org.springframework.data.repository.CrudRepository;

public interface MappedClassRepository extends CrudRepository<ExtenderClass, Integer>
{
}

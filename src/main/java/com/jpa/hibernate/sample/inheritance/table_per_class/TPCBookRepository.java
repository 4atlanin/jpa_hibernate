package com.jpa.hibernate.sample.inheritance.table_per_class;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TPCBookRepository extends JpaRepository<TPCBook, Integer>
{
}

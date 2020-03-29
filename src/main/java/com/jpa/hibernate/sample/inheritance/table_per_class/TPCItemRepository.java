package com.jpa.hibernate.sample.inheritance.table_per_class;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TPCItemRepository extends JpaRepository<TPCItem, Integer>
{
}

package com.jpa.hibernate.sample.inheritance.joined;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedItemRepository extends JpaRepository<JoinedItem, Integer>
{
}

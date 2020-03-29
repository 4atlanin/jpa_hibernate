package com.jpa.hibernate.sample.inheritance.joined;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinedBookRepository extends JpaRepository<JoinedBook, Integer>
{
}

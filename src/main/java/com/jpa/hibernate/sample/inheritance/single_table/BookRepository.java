package com.jpa.hibernate.sample.inheritance.single_table;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>
{
}

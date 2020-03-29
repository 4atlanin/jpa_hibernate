package com.jpa.hibernate.sample.inheritance.joined;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsPaperRepository extends JpaRepository<NewsPaper, Integer>
{
}

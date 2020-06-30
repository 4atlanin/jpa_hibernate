package com.jpa.hibernate.sample.configuration;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class JPAHibernateConfiguration
{
/*    @Bean
    public EntityManagerFactory entityManagerFactory( DataSource dataSource )
    {
        ServiceRegistry serviceRegistry = StandardServiceRegistryBuilder()
            .applySettings().build();

        return em.b;
    }*/
}

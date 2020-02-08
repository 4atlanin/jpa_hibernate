package com.jpa.hibernate.sample;

import com.jpa.hibernate.sample.entity.table.TableSample;
import com.jpa.hibernate.sample.repository.table.TableSampleRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

@SpringBootTest
@ContextConfiguration( initializers = { JpaHibernateBaseTest.Initializer.class})
public class JpaHibernateBaseTest
{

    private static final String DB_URL = "spring.datasource.url";
    private static final String DB_USERNAME = "spring.datasource.username";
    private static final String DB_PASSWORD = "spring.datasource.password";
    private static final Logger logger = LoggerFactory.getLogger(JpaHibernateApplication.class);

    static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer( "mysql:5.7.12" )
        .withUsername( "root" )
        .withPassword( "" )
        .withDatabaseName("jpa_hibernate")
        .withInitScript( "sql/init.sql" )
        .withLogConsumer(new Slf4jLogConsumer(logger));

    static class Initializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext>
    {
        public void initialize( ConfigurableApplicationContext configurableApplicationContext )
        {
            mySQLContainer.start();

            TestPropertyValues.of(
                DB_URL + '=' + mySQLContainer.getJdbcUrl(),
                DB_USERNAME + '=' + mySQLContainer.getUsername(),
                DB_PASSWORD + '=' + mySQLContainer.getPassword()
            ).applyTo( configurableApplicationContext.getEnvironment() );
        }
    }

}

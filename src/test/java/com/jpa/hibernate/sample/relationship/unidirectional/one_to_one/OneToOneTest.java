package com.jpa.hibernate.sample.relationship.unidirectional.one_to_one;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDOne;
import com.jpa.hibernate.sample.entity.relationship.unidirectional.one_to_one.EntityOTOUDTwo;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDOneRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_one.EntityOTOUDTwoRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OneToOneTest extends JpaHibernateBaseTest
{
    @Autowired
    private EntityOTOUDOneRepository otoudOneRepository;

    @Autowired
    private EntityOTOUDTwoRepository otoudtwoRepository;

    @Test
    public void unidirectionalSimple()
    {

        //нет каскадов, поэтому вручную сохраяем зависимую энтитю
        EntityOTOUDTwo two = new EntityOTOUDTwo();
        two.setPayload( "payload" );
        otoudtwoRepository.save( two );

        EntityOTOUDOne one = new EntityOTOUDOne();
        one.setTwo( two );

        one = otoudOneRepository.save( one );

        assertThrows( LazyInitializationException.class, otoudOneRepository.findById( one.getId() ).get().getTwo()::getPayload );


        // Если брать просто ID, то LazyInitializationException не кидается. Т.к id уже есть в энтити.
        //assertThrows( LazyInitializationException.class, otoudOneRepository.findById( one.getId() ).get().getTwo()::getId );



    }
}

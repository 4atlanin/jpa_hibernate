package com.jpa.hibernate.sample.relationship.unidirectional.one_to_many;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many.OTMUDManySideRepository;
import com.jpa.hibernate.sample.repository.relationship.unidirectional.one_to_many.OTMUDOneSideRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class OneToManyTest extends JpaHibernateBaseTest
{
    @Autowired
    private OTMUDManySideRepository manySideRepository;

    @Autowired
    private OTMUDOneSideRepository oneSideRepository;

    @Test
    public void simpleJoinTableTest()
    {
        //нету каскадов, поэтому сохраняем заранее
        OTMUDManySide manyFirst = manySideRepository.save( getNextManySide() );
        OTMUDManySide manySecond = manySideRepository.save( getNextManySide() );

        OTMUDOneSide one = new OTMUDOneSide();
        one.setMany( Arrays.asList( manyFirst, manySecond ) );
        oneSideRepository.save( one );

        assertTrue( true );
    }

    private OTMUDManySide getNextManySide()
    {
        OTMUDManySide entity = new OTMUDManySide();

        entity.setPayload( RandomString.make( 10 ) );
        return entity;
    }

    @Test
    public void customJoinTableTest()
    {
        //нету каскадов, поэтому сохраняем заранее
        OTMUDManySide manyFirst = manySideRepository.save( getNextManySide() );
        OTMUDManySide manySecond = manySideRepository.save( getNextManySide() );

        OTMUDOneSide one = new OTMUDOneSide();
        one.setManyCustom( Arrays.asList( manyFirst, manySecond ) );
        oneSideRepository.save( one );

        assertTrue( true );
    }

    @Test
    public void otmWithoutLinkingTable()
    {
        OTMUDManySide manyFirst = manySideRepository.save( getNextManySide() );
        OTMUDManySide manySecond = manySideRepository.save( getNextManySide() );

        OTMUDOneSide one = new OTMUDOneSide();
        one.setManyWithoutThirdTable( Arrays.asList( manyFirst, manySecond ) );
        one = oneSideRepository.save( one );

        oneSideRepository.findById( one.getId() );
    }

}

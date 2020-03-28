package com.jpa.hibernate.sample.relationship.bidirectional.many_to_many;

import com.jpa.hibernate.sample.JpaHibernateBaseTest;
import com.jpa.hibernate.sample.relationship.biderectional.many_to_many.ManyLeftSide;
import com.jpa.hibernate.sample.relationship.biderectional.many_to_many.ManyRightSide;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.many_to_many.MTMBDLeftRepository;
import com.jpa.hibernate.sample.repository.relationship.bidirectional.many_to_many.MTMBDRightRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/cleanup.sql" )
public class ManyToManyTest extends JpaHibernateBaseTest
{
    @Autowired
    private MTMBDLeftRepository leftRepository;

    @Autowired
    private MTMBDRightRepository rightRepository;

    @Test
    public void mmTest()
    {
        ManyRightSide mr1 = new ManyRightSide();
        ManyRightSide mr2 = new ManyRightSide();

        mr1.setRightString( RandomString.make( 10 ) );
        mr2.setRightString( RandomString.make( 10 ) );

        ManyLeftSide ml1 = new ManyLeftSide();
        ManyLeftSide ml2 = new ManyLeftSide();

        ml1.setLeftString( RandomString.make( 10 ) );
        ml2.setLeftString( RandomString.make( 10 ) );

        mr1.setLeftSide( Arrays.asList( ml1, ml2 ) );
        mr2.setLeftSide( Arrays.asList( ml1, ml2 ) );

        ml1.setRightSide( Arrays.asList( mr1, mr2 ) );
        ml2.setRightSide( Arrays.asList( mr1, mr2 ) );

        mr1 = rightRepository.save( mr1 );
        mr2 = rightRepository.save( mr2 );

        ml1 = leftRepository.save( ml1 );
        ml2 = leftRepository.save( ml2 );

        ManyRightSide lieCheckRight = rightRepository.findById( mr1.getId() ).get();

        assertThrows( LazyInitializationException.class, () -> lieCheckRight.getLeftSide().get( 0 ).getLeftString() );

        ManyLeftSide lieCheckLeft = leftRepository.findById( ml1.getId() ).get();
        assertThrows( LazyInitializationException.class, () -> lieCheckLeft.getRightSide().get( 0 ).getRightString() );

    }

}


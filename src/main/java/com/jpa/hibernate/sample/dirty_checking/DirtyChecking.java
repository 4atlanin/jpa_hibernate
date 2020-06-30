package com.jpa.hibernate.sample.dirty_checking;

import com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDManySide;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity( name = "dirty_checking" )
//@Access( AccessType.PROPERTY )
public class DirtyChecking
{
    private int id;

    // Пример 1
    // по умолчанию Создаст таблицу связи
  //  @OneToMany
    private List<OTMUDManySide> many;

    public DirtyChecking()
    {
    }

    public int hashCode()
    {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $many = this.getMany();
        result = result * PRIME + ( $many == null ? 43 : $many.hashCode() );

        result = result * PRIME;
        return result;
    }

    public boolean equals( final Object o )
    {
        if( o == this )
        {
            return true;
        }
        if( !( o instanceof com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDOneSide ) )
        {
            return false;
        }
        final com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDOneSide other =
            (com.jpa.hibernate.sample.relationship.unidirectional.one_to_many.OTMUDOneSide) o;
        if( this.getId() != other.getId() )
        {
            return false;
        }
        final Object this$many = this.getMany();
        final Object other$many = other.getMany();
        if( this$many == null ? other$many != null : !this$many.equals( other$many ) )
        {
            return false;
        }
        return true;
    }

    public String toString()
    {
        return "OTMUDOneSide(id=" + this.getId() + ", many=" + this.getMany() + ", manyCustom=" + ")";
    }

    @Id                         // т.к. @Id над геттером, то и Hibernate будет обращаться к полям через методы.
    @GeneratedValue
    @Column( name = "otmudo_id" )
    public int getId()
    {
        return this.id;
    }

    @OneToMany
    public List<OTMUDManySide> getMany()
    {
        return Collections.unmodifiableList( this.many );
    }

    public void setMany( List<OTMUDManySide> many )
    {
        this.many = many;
    }

    public void setId( int id )
    {
        this.id = id;
    }
}

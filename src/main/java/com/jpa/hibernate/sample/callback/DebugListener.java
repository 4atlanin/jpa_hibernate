package com.jpa.hibernate.sample.callback;

import javax.persistence.*;

public class DebugListener
{
    @PrePersist
    void prePersist( Object object )  //Object, чтобы можно было вешать на любую энтитю
    {
        System.out.println( "prePersist DebugListener" );
    }

    @PreUpdate
    void preUpdate( Object object )
    {
        System.out.println( "preUpdate DebugListener" );
    }

    @PreRemove
    void preRemove( Object object )
    {
        System.out.println( "preRemove DebugListener" );
    }

    @PostPersist
    void postPersist( Object object )  //O
    {
        System.out.println( "postPersist DebugListener" );
    }

    @PostUpdate
    void postUpdate( Object object )
    {
        System.out.println( "postUpdate DebugListener" );
    }

    @PostRemove
    void postRemove( Object object )
    {
        System.out.println( "postRemove DebugListener" );
    }

    @PostLoad
    void postLoad( Object object )
    {
        System.out.println( "postLoad DebugListener" );
    }

}

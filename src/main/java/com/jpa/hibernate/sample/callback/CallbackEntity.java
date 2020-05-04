package com.jpa.hibernate.sample.callback;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table( name = "callback_entity" )
@EntityListeners( DebugListener.class )
public class CallbackEntity
{
    @Id
    @GeneratedValue
    private int id;

    private String firstName;
    private String lastName;

    @Transient
    @Setter
    private String name;

    @PrePersist
    @PreUpdate
    @PreRemove
    private void splitName()
    {
        final String[] s;
        if( name != null )
        {
            s = name.split( " " );
            firstName = s[0];
            lastName = s[1];
            System.out.println( "PrePersist Callback" );
        }
        else
        {
            System.out.println( "PreUpdate Callback" );
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void joinName()
    {
        name = firstName + ' ' + lastName + '!';
        System.out.println( "Post Callback" );
    }

    @PostLoad
    private void postLoad()
    {
        name = firstName + ' ' + lastName + "afterload";
        System.out.println( "PostLoad Callback with " + name );
    }
}

package com.jpa.hibernate.sample.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "joined_book" )
@NoArgsConstructor
@DiscriminatorValue( "B" )
public class JoinedBook extends JoinedItem
{
    @Column( name = "title" )
    private String title;
}

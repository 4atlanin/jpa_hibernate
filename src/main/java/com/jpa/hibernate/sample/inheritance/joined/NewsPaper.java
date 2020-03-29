package com.jpa.hibernate.sample.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table( name = "joined_newspaper" )
@NoArgsConstructor
@DiscriminatorValue( "C" )
public class NewsPaper extends JoinedItem
{
    @Column( name = "pages" )
    private int pages;
}

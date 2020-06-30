package com.jpa.hibernate.sample.inheritance.default_behaviour;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "default_base_class" )
public class BaseClass
{
    @Id
    @GeneratedValue
    @Column( name = "dbc_id" )
    private int id;

    @Column( name = "dbc_content" )
    private String content = "anyString";
}

package com.jpa.hibernate.sample.relationship.order.by;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table( name = "company_order_by" )
public class Company
{
    @Id
    @GeneratedValue
    @Column( name = "cob_id" )
    private int id;

    @Column( name = "cob_name" )
    private String name;

    @OneToMany( cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    @OrderBy( "name ASC" )   //можно включать несколько полей: OrderBy("name DESC, id ASC");  Можно указывать как поля энтити так и колонки в БД
    @JoinColumn( name = "cob_id", nullable = false )
    private List<Location> locations;

    public Company( String name )
    {
        this.name = name;
    }
}

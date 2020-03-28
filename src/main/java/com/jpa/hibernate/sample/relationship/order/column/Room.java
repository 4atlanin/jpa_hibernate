package com.jpa.hibernate.sample.relationship.order.column;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@javax.persistence.Table( name = "room_with_order_column" )
public class Room
{
    @Id
    @GeneratedValue
    @Column( name = "rwoc_id" )
    private int id;

    @Column( name = "rwoc_name" )
    private String name;

    @OneToMany( cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    @JoinColumn( name = "rwoc_id", nullable = false )  //без неё будет 3-я таблица, таблица связи
    @OrderColumn( name = "room_order_column" )    //запилит интовую колонку с таким названием. Порядок будет определятся цифрами в ячейках, которые зависят от порядка вставки.
    private List<Table> tables;

    public Room( String name )
    {
        this.name = name;
    }
}

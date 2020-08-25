package com.jpa.hibernate.sample.batch;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "item_batch" )
@NoArgsConstructor
public class ItemBatch
{
    @ToString.Exclude
    @Id
    @GeneratedValue
    @Column( name = "ib_id" )
    public int id;
    
    @ToString.Exclude
    @Column( name = "ib_value" )
    public String value;
    
    @ToString.Exclude
    @OneToMany( mappedBy = "itemBatch", cascade = CascadeType.ALL )
    @BatchSize( size = 1 )
    public Set<BidBatch> bids = new HashSet<>();

    public ItemBatch( String value )
    {
        this.value = value;
    }

    public void setBids( BidBatch bids )
    {
        this.bids.add( bids );
        bids.setItemBatch( this );
    }
}

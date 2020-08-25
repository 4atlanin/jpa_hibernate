package com.jpa.hibernate.sample.batch;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table( name = "bid_batch" )
@NoArgsConstructor
public class BidBatch
{
    @Id
    @GeneratedValue
    @Column( name = "bb_id" )
    public int id;

    @Column( name = "bb_value" )
    public String value;

    @ManyToOne( optional = false, cascade = CascadeType.ALL )
    private ItemBatch itemBatch;

    public BidBatch( String value )
    {
        this.value = value;
    }

    public void setItemBatch( ItemBatch itemBatch )
    {
        this.itemBatch = itemBatch;
    }

    @Override
    public String toString()
    {
        return null;
    }

}

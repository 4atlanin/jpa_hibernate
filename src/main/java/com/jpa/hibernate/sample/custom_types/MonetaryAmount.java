package com.jpa.hibernate.sample.custom_types;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
public class MonetaryAmount implements Serializable
{
    private static final long serialVersionUID = 5481256841468330517L;
    private final BigDecimal value;
    private final Currency currency;

    public static MonetaryAmount fromString( String s )
    {
        String[] split = s.split( " " );
        return new MonetaryAmount( new BigDecimal( split[0] ), Currency.getInstance( split[1] ) );
    }

    public String toString()
    {
        return getValue() + " " + getCurrency();
    }
}
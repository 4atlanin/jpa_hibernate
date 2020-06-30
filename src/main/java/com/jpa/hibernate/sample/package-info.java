@TypeDefs( {
               @TypeDef(
                   name = "monetary_amount_usd",
                   typeClass = MonetaryAmountUserType.class,
                   parameters = { @Parameter( name = "convertTo", value = "USD" ) }
               ),
               @TypeDef(
                   name = "monetary_amount_eur",
                   typeClass = MonetaryAmountUserType.class,
                   parameters = { @Parameter( name = "convertTo", value = "EUR" ) }
               ),
               @TypeDef( name = "mytype_varchar", typeClass = MyCustomBasicStringType.class )
           } )
package com.jpa.hibernate.sample;

import com.jpa.hibernate.sample.configuration.basic_type_conversion.MyCustomBasicStringType;
import com.jpa.hibernate.sample.configuration.user_type_conversion.MonetaryAmountUserType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
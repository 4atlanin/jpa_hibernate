package com.jpa.hibernate.sample.configuration.basic_type_conversion;

import com.jpa.hibernate.sample.custom_types.MyCustomType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;

public class MyCustomBasicStringStringJavaDescriptor extends AbstractTypeDescriptor<MyCustomType>
{
    public static final MyCustomBasicStringStringJavaDescriptor INSTANCE =
        new MyCustomBasicStringStringJavaDescriptor();

    public MyCustomBasicStringStringJavaDescriptor()
    {
        super( MyCustomType.class, ImmutableMutabilityPlan.INSTANCE );
    }

    @Override
    public MyCustomType fromString( String string )
    {
        return null;
    }

    @Override
    public <X> X unwrap( MyCustomType value, Class<X> type, WrapperOptions options )
    {
        if( value == null )
        {
            return null;
        }

        if( String.class.isAssignableFrom( type ) )
        {
            return (X) value.getContent();
        }

        throw unknownUnwrap( type );
    }

    @Override
    public <X> MyCustomType wrap( X value, WrapperOptions options )
    {
        if( value == null )
        {
            return null;
        }

        if( String.class.isInstance( value ) )
        {
            return new MyCustomType();
        }

        throw unknownWrap( value.getClass() );
    }
}

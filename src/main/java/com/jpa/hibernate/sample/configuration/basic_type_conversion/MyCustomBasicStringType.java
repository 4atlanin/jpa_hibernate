package com.jpa.hibernate.sample.configuration.basic_type_conversion;

import com.jpa.hibernate.sample.custom_types.MyCustomType;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class MyCustomBasicStringType extends AbstractSingleColumnStandardBasicType<MyCustomType>
{
    public static final MyCustomBasicStringType INSTANCE = new MyCustomBasicStringType();

    public MyCustomBasicStringType()
    {
        // вместо VarcharTypeDescriptor можно было тоже заимплементить свою хрень, но смысл, если есть готовая
        super( VarcharTypeDescriptor.INSTANCE, MyCustomBasicStringStringJavaDescriptor.INSTANCE );
    }

    @Override
    public String getName()
    {
        return "MyCustomBasicStringType";
    }
}

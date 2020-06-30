package com.jpa.hibernate.sample.configuration.user_type_conversion;

import com.jpa.hibernate.sample.custom_types.MonetaryAmount;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Properties;

//CompositeUserType - для позволяет указать из чего состоит тип, и потом обращаться к этим частям в JPQL через точку.
// ParameterizedUserType - добавляет настройки маппинга в клас адаптера. Это то же, что и " parameters = { @Parameter( name = "convertTo", value = "EUR" ) }"
// аннотации @TypeDef
//DynamicParameterizedType - тоже что и ParameterizedUserType, но имеет больше инфы про названия таблиц, колонок и тд

public class MonetaryAmountUserType implements CompositeUserType, DynamicParameterizedType
{
    protected Currency convertTo;

    // TODO Порядок важен, должен быть тот же что и порядок @Column над полем
    @Override
    public String[] getPropertyNames()
    {
        return new String[] { "value", "currency" };
    }

    // TODO Порядок важен, должен быть тот же что и порядок @Column над полем
    @Override
    public Type[] getPropertyTypes()
    {
        return new Type[] { StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.CURRENCY };
    }

    @Override
    public Object getPropertyValue( Object component, int property ) throws HibernateException
    {
        MonetaryAmount monetaryAmount = (MonetaryAmount) component;
        if( property == 0 )
        {
            return monetaryAmount.getValue();
        }
        else
        {
            return monetaryAmount.getCurrency();
        }
    }

    @Override
    public void setPropertyValue( Object component, int property, Object value ) throws HibernateException
    {
        throw new UnsupportedOperationException( "Monetary amount is immutable" );
    }

    // Возвращает адаптируемый класс
    @Override
    public Class returnedClass()
    {
        return MonetaryAmount.class;
    }

    //Определяет, менялось ли значение
    // Нужен, чтобы определить факт изменения и необходимости записи в базу данных.
    @Override
    public boolean equals( Object x, Object y ) throws HibernateException
    {
        return x == y || !( x == null || y == null ) && x.equals( y );
    }

    @Override
    public int hashCode( Object x ) throws HibernateException
    {
        return x.hashCode();
    }

    // Читает ResultSet когда нужный объект достаётся из БД, возвращает готовый MonetaryAmount
    @Override
    public Object nullSafeGet( ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner ) throws HibernateException, SQLException
    {
        if( rs.wasNull() )
        {
            return null;
        }

        BigDecimal amount = rs.getBigDecimal( names[0] );
        Currency currency = Currency.getInstance( rs.getString( names[1] ) );

        return new MonetaryAmount( amount, currency );

    }

    // TODO Порядок важен, должен быть тот же что и порядок @Column над полем
    // Вызывается, когда нужно сохранить MonetaryAmount в БД. Сохраняет значения в PreparedStatement
    @Override
    public void nullSafeSet( PreparedStatement st, Object value, int index, SharedSessionContractImplementor session ) throws HibernateException, SQLException
    {
        if( value == null )
        {
            st.setNull( index, StandardBasicTypes.BIG_DECIMAL.sqlType() );
            st.setNull( index + 1, StandardBasicTypes.CURRENCY.sqlType() );
        }
        else
        {
            MonetaryAmount amount = (MonetaryAmount) value;
            MonetaryAmount dbAmount = convert( amount, convertTo );
            st.setBigDecimal( index, dbAmount.getValue() );
            st.setString( index + 1, convertTo.getCurrencyCode() );
        }

    }

    // метод просто умножает на два при каждом сохранении
    private MonetaryAmount convert( MonetaryAmount amount, Currency convertTo )
    {
        return new MonetaryAmount( amount.getValue().multiply( new BigDecimal( 2 ) ), convertTo );
    }

    // Если Хиберу нужно будет сделать копию значения, то вызовется этот метод.
    // Для не мутабельных объектов можно вернуть сам объект

    @Override
    public Object deepCopy( Object value ) throws HibernateException
    {
        return value;
    }

    // если клас не мутабельный, то может будет оптимизация
    @Override
    public boolean isMutable()
    {
        return false;
    }

    // Метод вызывается, когда значение сохраняется в кэше второго уровня.
    // Возвращает экземпляр Serializable, можно или String или сам MonetaryAmount
    @Override
    public Serializable disassemble( Object value, SharedSessionContractImplementor session ) throws HibernateException
    {
        return value.toString();
    }

    // Метод вызывается, когда сериализованное значение читается из кэша второго уровня
    // т.к. Сериализовали строку, то и восстанавливаем из строки 
    @Override
    public Object assemble( Serializable cached, SharedSessionContractImplementor session, Object owner ) throws HibernateException
    {
        return MonetaryAmount.fromString( (String) cached );
    }

    // вызывается для метода EntityManager#merge, метод должен вернуть копию оригинала.
    // Для не мутабельных объектов можно исходный объект
    @Override
    public Object replace( Object original, Object target, SharedSessionContractImplementor session, Object owner ) throws HibernateException
    {
        return original;
    }

    @Override
    public void setParameterValues( Properties parameters )
    {
        // обращение к динамически параметрам, имена колонок и таблиц, аннотации над колонками и тд
        ParameterType parameterType = (ParameterType) parameters.get( PARAMETER_TYPE );
        String[] columns = parameterType.getColumns();
        String table = parameterType.getTable();
        Annotation[] annotations = parameterType.getAnnotationsMethod();

        String convertToParameter = parameters.getProperty( "convertTo" );
        this.convertTo = Currency.getInstance( convertToParameter != null ? convertToParameter : "USD" );
    }
}

package com.netappsid.binding.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.netappsid.binding.value.AbstractValueModel;
import com.netappsid.binding.value.ValueModel;

public class BeanPropertyValueModel extends AbstractValueModel
{
	private static final Logger LOGGER = Logger.getLogger(BeanPropertyValueModel.class);
	
	private final ValueModel beanChannel;
	private final String propertyName;

	public BeanPropertyValueModel(ValueModel beanChannel, String propertyName)
	{
		this.beanChannel = beanChannel;
		this.propertyName = propertyName;
	}

	public String getPropertyName()
	{
		return propertyName;
	}
	
	public PropertyDescriptor getPropertyDescriptor()
	{
		return getPropertyDescriptor(beanChannel.getValue());
	}

	public Object getValue()
	{
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor();

		return propertyDescriptor != null ? BeanUtils.getValue(beanChannel.getValue(), propertyDescriptor) : null;
	}

	public void setValue(Object newValue)
	{
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor();

		if (propertyDescriptor != null)
		{
			try
			{
				BeanUtils.setValue(beanChannel.getValue(), propertyDescriptor, newValue);
			}
			catch (PropertyVetoException e)
			{
				LOGGER.trace(e.getMessage(), e);
			}
		}
	}

	protected void fireChange(Object currentBean)
	{
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor(currentBean);
		
		if (propertyDescriptor != null && propertyDescriptor.getReadMethod() != null)
		{
			fireValueChange(null, BeanUtils.getValue(currentBean, propertyDescriptor));
		}
		else
		{
			fireValueChange(null, null);
		}
	}

	protected void setBean(Object oldBean, Object newBean)
	{
		final PropertyDescriptor oldBeanPropertyDescriptor = getPropertyDescriptor(oldBean);
		final PropertyDescriptor newBeanPropertyDescriptor = getPropertyDescriptor(newBean);
		
		Object oldValue = null;
		Object newValue = null;

		if (oldBeanPropertyDescriptor != null && oldBeanPropertyDescriptor.getReadMethod() != null)
		{
			oldValue = BeanUtils.getValue(oldBean, oldBeanPropertyDescriptor);
		}
		
		if (newBeanPropertyDescriptor != null && newBeanPropertyDescriptor.getReadMethod() != null)
		{
			newValue = BeanUtils.getValue(newBean, newBeanPropertyDescriptor);
		}

		if (oldValue != null || newValue != null)
		{
			fireValueChange(oldValue, newValue, true);
		}
	}
	
	@Override
	protected String paramString()
	{
		Object bean = this.beanChannel.getValue();
		String beanType = null;
		Object value = getValue();
		String valueType = null;
		String propertyDescriptorName = null;
		String propertyType = null;
		Method propertySetter = null;
		
		if (bean != null)
		{
			beanType = bean.getClass().getName();
			valueType = value == null ? null : value.getClass().getName();
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean);
			propertyDescriptorName = propertyDescriptor != null ? propertyDescriptor.getName() : null;
			propertyType = propertyDescriptor != null ? propertyDescriptor.getPropertyType().getName() : null;
			propertySetter = propertyDescriptor != null ? propertyDescriptor.getWriteMethod() : null;
		}
		
		return "bean=" + bean + "; bean type=" + beanType + "; value=" + value + "; value type=" + valueType + "; property name=" + propertyDescriptorName
				+ "; property type=" + propertyType + "; property setter=" + propertySetter;
	}
	
	private PropertyDescriptor getPropertyDescriptor(Object bean)
	{
		try
		{
			return bean != null ? BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName) : null;
		}
		catch (Exception e)
		{
			LOGGER.trace(e.getMessage(), e);
		}

		return null;
	}
}
package com.netappsid.binding.beans;

import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.netappsid.binding.value.AbstractValueModel;

public class SimplePropertyAdapter extends AbstractValueModel
{
	private static final Logger LOGGER = Logger.getLogger(SimplePropertyAdapter.class);
	
	private final BeanAdapter beanAdapter;
	private final String propertyName;

	public SimplePropertyAdapter(BeanAdapter beanAdapter, String propertyName)
	{
		super(beanAdapter.getChangeSupportFactory());
		this.beanAdapter = beanAdapter;
		this.propertyName = propertyName;
	}

	public String getPropertyName()
	{
		return propertyName;
	}
	
	public PropertyDescriptor getPropertyDescriptor()
	{
		return getPropertyDescriptor(beanAdapter.getBean());
	}

	public Object getValue()
	{
		final PropertyDescriptor propertyDescriptor = getPropertyDescriptor();

		return propertyDescriptor != null ? BeanUtils.getValue(beanAdapter.getBean(), propertyDescriptor) : null;
	}

	public void setValue(Object newValue)
	{
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor();

		if(propertyDescriptor == null)
		{
			try
			{
				Object newInstance = beanAdapter.getBeanClass().newInstance();
				beanAdapter.getBeanChannel().setValue(newInstance);
				propertyDescriptor = getPropertyDescriptor();
			}
			catch (Exception e)
			{
				LOGGER.error(e, e);
			}
		}
		
		if (propertyDescriptor != null)
		{
			try
			{
				BeanUtils.setValue(beanAdapter.getBean(), propertyDescriptor, newValue);
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
		Object bean = this.beanAdapter.getBean();
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
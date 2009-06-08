package com.netappsid.binding;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;

import com.jgoodies.binding.beans.BeanUtils;
import com.netappsid.utils.ReflectionUtils;

public class PresentationModelFactory
{
	private PresentationModelFactory()
	{
	// Hidden constructor
	}

	public static PresentationModel createPresentationModel(PresentationModel parentModel)
	{
		return new DefaultPresentationModel(parentModel.getBeanClass());
	}
	
	public static PresentationModel createPresentationModel(PresentationModel parentModel, String propertyName)
	{
		PresentationModel presentationModel = null;
		PropertyDescriptor propertyDescriptor = getPropertyDescriptor(parentModel.getBeanClass(), propertyName);

		if (propertyDescriptor == null)
		{
			throw new IllegalArgumentException();
		}
		else if (List.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
		{
			presentationModel = new SelectionPresentationModel(getGenericReturnType(propertyDescriptor), parentModel.getValueModel(propertyName));
		}
		else
		{
			presentationModel = new DefaultPresentationModel(propertyDescriptor.getPropertyType(), parentModel.getValueModel(propertyName));
		}

		return presentationModel;
	}

	private static Class<?> getGenericReturnType(PropertyDescriptor propertyDescriptor)
	{
		return ReflectionUtils.extractType(propertyDescriptor.getReadMethod().getGenericReturnType());
	}

	private static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName)
	{
		try
		{
			return BeanUtils.getPropertyDescriptor(beanClass, propertyName);
		}
		catch (IntrospectionException e)
		{
			return null;
		}
	}
}

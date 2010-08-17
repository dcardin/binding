package com.netappsid.binding;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import com.netappsid.binding.beans.BeanUtils;
import com.netappsid.utils.ReflectionUtils;

public class PresentationModelFactory
{
	private PresentationModelFactory()
	{
	// Hidden constructor
	}
	
	public static PresentationModel createPresentationModel(PresentationModel parentModel, String propertyName)
	{
		PresentationModel presentationModel = null;
		
		if (parentModel instanceof DefaultPresentationModel)
		{
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(parentModel.getBeanClass(), propertyName);

			if (propertyDescriptor == null)
			{
				throw new IllegalArgumentException();
			}
			else if (List.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
			{
				presentationModel = new SelectionPresentationModel(getGenericReturnType(propertyDescriptor), parentModel.getValueModel(propertyName));
			}
			else if (Map.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
			{
				presentationModel = new DynamicPresentationModel(parentModel.getValueModel(propertyName));
			}
			else
			{
				presentationModel = new DefaultPresentationModel(propertyDescriptor.getPropertyType(), parentModel.getValueModel(propertyName));
			}
		}
		else if (parentModel instanceof DynamicPresentationModel)
		{
			//TODO How can we create a PresentationModel if the value is null since it's not possible to know the bean class?
		}
		else if (parentModel instanceof SelectionPresentationModel)
		{
			presentationModel = new DefaultPresentationModel(parentModel.getBeanClass());
		}
		
		if (presentationModel != null)
		{
			presentationModel.setParentModel(parentModel);
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

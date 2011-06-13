package com.netappsid.binding;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;

import com.netappsid.binding.beans.BeanUtils;

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
				presentationModel = new SelectionPresentationModel(parentModel.getChangeSupportFactory(), getGenericReturnType(propertyDescriptor),
						parentModel.getValueModel(propertyName));
			}
			else if (Map.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
			{
				presentationModel = new DynamicPresentationModel(parentModel.getChangeSupportFactory(), parentModel.getValueModel(propertyName));
			}
			else
			{
				presentationModel = new DefaultPresentationModel(parentModel.getChangeSupportFactory(), propertyDescriptor.getPropertyType(),
						parentModel.getValueModel(propertyName));
			}
		}
		else if (parentModel instanceof DynamicPresentationModel)
		{
			// TODO How can we create a PresentationModel if the value is null since it's not possible to know the bean class?
		}
		else if (parentModel instanceof SelectionPresentationModel)
		{
			presentationModel = new DefaultPresentationModel(parentModel.getChangeSupportFactory(), parentModel.getBeanClass());
		}

		if (presentationModel != null)
		{
			presentationModel.setParentModel(parentModel);
		}

		return presentationModel;
	}

	private static Class<?> getGenericReturnType(PropertyDescriptor propertyDescriptor)
	{
		return extractType(propertyDescriptor.getReadMethod().getGenericReturnType());
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
	
	private static Class<?> extractType(Type t)
	{
		if (t != null && t instanceof ParameterizedType)
		{
			ParameterizedType pt = (ParameterizedType) t;
			Type[] genTypes = pt.getActualTypeArguments();
			if (genTypes.length == 1 && genTypes[0] instanceof Class)
			{
				return (Class<?>) genTypes[0];
			}
			// This is used for the typed class
			else if (genTypes.length == 1 && genTypes[0] instanceof WildcardType)
			{
				WildcardType wildcardType = (WildcardType) genTypes[0];
				Type[] extendGenTypes = wildcardType.getUpperBounds();
				if (extendGenTypes.length == 1 && extendGenTypes[0] instanceof Class)
				{
					return (Class<?>) extendGenTypes[0];
				}
			}
			else if (genTypes.length == 2 && genTypes[1] instanceof Class)
			{
				return (Class<?>) genTypes[1];
			}
		}
		return null;
	}
}

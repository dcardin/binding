package com.netappsid.binding;

import java.beans.IntrospectionException;
import java.util.List;

import com.jgoodies.binding.beans.BeanUtils;
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
			if (isListType(parentModel.getBeanClass(), propertyName))
			{
				presentationModel = new SelectionPresentationModel(getGenericReturnType(parentModel.getBeanClass(), propertyName), parentModel
						.getValueModel(propertyName));
				presentationModel.setParentModel(parentModel);
			}
			else
			{
				presentationModel = new DefaultPresentationModel(getReturnType(parentModel.getBeanClass(), propertyName), parentModel
						.getValueModel(propertyName));
				presentationModel.setParentModel(parentModel);
			}
		}
		else if (parentModel instanceof SelectionPresentationModel)
		{
			presentationModel = new DefaultPresentationModel(parentModel.getBeanClass(), ((SelectionPresentationModel) parentModel)
					.getSelectedBeanChannel(propertyName));
		}

		return presentationModel;
	}

	private static Class<?> getGenericReturnType(Class<?> beanClass, String propertyName)
	{
		try
		{
			return ReflectionUtils.extractType(BeanUtils.getPropertyDescriptor(beanClass, propertyName).getReadMethod().getGenericReturnType());
		}
		catch (IntrospectionException ex)
		{
			return Object.class;
		}
	}

	private static Class<?> getReturnType(Class<?> beanClass, String propertyName)
	{
		try
		{
			return BeanUtils.getPropertyDescriptor(beanClass, propertyName).getPropertyType();
		}
		catch (IntrospectionException ex)
		{
			return Object.class;
		}
	}

	private static boolean isListType(Class<?> beanClass, String propertyName)
	{
		try
		{
			return BeanUtils.getPropertyDescriptor(beanClass, propertyName).getPropertyType() == List.class;
		}
		catch (IntrospectionException ex)
		{
			return false;
		}
	}
}

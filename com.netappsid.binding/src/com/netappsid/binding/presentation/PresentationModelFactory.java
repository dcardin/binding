package com.netappsid.binding.presentation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.netappsid.binding.beans.BeanAdapterFactory;
import com.netappsid.binding.beans.BeanUtils;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;
import com.netappsid.utils.ReflectionUtils;
import com.netappsid.validate.Validate;

public class PresentationModelFactory
{
	private final ChangeSupportFactory changeSupportFactory;
	private final BeanAdapterFactory beanAdapterFactory;

	@Inject
	public PresentationModelFactory(ChangeSupportFactory changeSupportFactory, BeanAdapterFactory beanAdapterFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
		this.beanAdapterFactory = beanAdapterFactory;
	}

	public PresentationModel createPresentationModel(PresentationModel parentModel, String propertyName)
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
				presentationModel = createSelectionPresentationModel(getGenericReturnType(propertyDescriptor), parentModel.getValueModel(propertyName));
			}
			else if (Map.class.isAssignableFrom(propertyDescriptor.getPropertyType()))
			{
				presentationModel = createDynamicPresentationModel(parentModel.getValueModel(propertyName));
			}
			else
			{
				presentationModel = create(propertyDescriptor.getPropertyType(), parentModel.getValueModel(propertyName));
			}
		}
		else if (parentModel instanceof DynamicPresentationModel)
		{
			// TODO How can we create a PresentationModel if the value is null since it's not possible to know the bean class?
		}
		else if (parentModel instanceof SelectionPresentationModel)
		{
			presentationModel = create(parentModel.getBeanClass());
		}

		if (presentationModel != null)
		{
			presentationModel.setParentModel(parentModel);
		}

		return presentationModel;
	}

	private Class<?> getGenericReturnType(PropertyDescriptor propertyDescriptor)
	{
		return ReflectionUtils.extractType(propertyDescriptor.getReadMethod().getGenericReturnType());
	}

	private PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName)
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
	
	public DefaultPresentationModel create(Class<?> beanClass)
	{
		return new DefaultPresentationModel(changeSupportFactory, beanAdapterFactory.create(new ValueHolder(null, true)), new StateModel(), beanClass);
	}
	
	public DefaultPresentationModel create(Class<?> beanClass, Object bean)
	{
		return new DefaultPresentationModel(changeSupportFactory, beanAdapterFactory.create(new ValueHolder(bean, true)), new StateModel(), beanClass);
	}

	public DefaultPresentationModel create(Class<?> beanClass, ValueModel beanChannel)
	{
		return new DefaultPresentationModel(changeSupportFactory, beanAdapterFactory.create(beanChannel), new StateModel(), beanClass);
	}

	public DynamicPresentationModel createDynamicPresentationModel(ValueModel mapChannel)
	{
		return new DynamicPresentationModel(changeSupportFactory, Validate.notNull(mapChannel, "Map Channel cannot be null."));
	}

	public SelectionPresentationModel createSelectionPresentationModel(Class<?> beanClass, ValueModel beanListChannel)
	{
		return new SelectionPresentationModel(changeSupportFactory, beanClass, beanListChannel);
	}
}

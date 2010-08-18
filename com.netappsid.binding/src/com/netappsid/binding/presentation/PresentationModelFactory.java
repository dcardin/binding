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

	public PresentationModel createSubModel(PresentationModel parentModel, String propertyName)
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
				presentationModel = createDefaultPresentationModel(propertyDescriptor.getPropertyType(), parentModel.getValueModel(propertyName));
			}
		}
		else if (parentModel instanceof DynamicPresentationModel)
		{
			// TODO How can we create a PresentationModel if the value is null since it's not possible to know the bean class?
		}
		else if (parentModel instanceof SelectionPresentationModel)
		{
			presentationModel = createDefaultPresentationModel(parentModel.getBeanClass());
		}

		if (presentationModel != null)
		{
			presentationModel.setParentModel(parentModel);
		}

		return presentationModel;
	}

	public DefaultPresentationModel createDefaultPresentationModel(Class<?> beanClass)
	{
		return new DefaultPresentationModel(this, changeSupportFactory, beanAdapterFactory.create(new ValueHolder(null, true)), new StateModel(), beanClass);
	}

	public DefaultPresentationModel createDefaultPresentationModel(Class<?> beanClass, Object bean)
	{
		return new DefaultPresentationModel(this, changeSupportFactory, beanAdapterFactory.create(new ValueHolder(bean, true)), new StateModel(), beanClass);
	}

	public DefaultPresentationModel createDefaultPresentationModel(Class<?> beanClass, ValueModel beanChannel)
	{
		return new DefaultPresentationModel(this, changeSupportFactory, beanAdapterFactory.create(beanChannel), new StateModel(), beanClass);
	}

	public DynamicPresentationModel createDynamicPresentationModel()
	{
		return new DynamicPresentationModel(this, changeSupportFactory, new ValueHolder());
	}

	public DynamicPresentationModel createDynamicPresentationModel(Map<String, ?> map)
	{
		return new DynamicPresentationModel(this, changeSupportFactory, new ValueHolder(map));
	}

	public DynamicPresentationModel createDynamicPresentationModel(ValueModel mapChannel)
	{
		return new DynamicPresentationModel(this, changeSupportFactory, Validate.notNull(mapChannel, "Map Channel cannot be null."));
	}

	public SelectionPresentationModel createSelectionPresentationModel(Class<?> beanClass)
	{
		return new SelectionPresentationModel(this, changeSupportFactory, beanClass, new ValueHolder(null, true));
	}

	public SelectionPresentationModel createSelectionPresentationModel(Class<?> beanClass, List<?> beanList)
	{
		return new SelectionPresentationModel(this, changeSupportFactory, beanClass, new ValueHolder(beanList, true));
	}

	public SelectionPresentationModel createSelectionPresentationModel(Class<?> beanClass, ValueModel beanListChannel)
	{
		return new SelectionPresentationModel(this, changeSupportFactory, beanClass, beanListChannel);
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
}

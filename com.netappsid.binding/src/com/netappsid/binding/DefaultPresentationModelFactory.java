package com.netappsid.binding;

import com.google.inject.Inject;
import com.netappsid.binding.beans.BeanAdapterFactory;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;

public class DefaultPresentationModelFactory
{
	private final ChangeSupportFactory changeSupportFactory;
	private final BeanAdapterFactory beanAdapterFactory;

	@Inject
	public DefaultPresentationModelFactory(ChangeSupportFactory changeSupportFactory, BeanAdapterFactory beanAdapterFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
		this.beanAdapterFactory = beanAdapterFactory;
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
}

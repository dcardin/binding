package com.netappsid.binding.beans;

import com.google.inject.Inject;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;

public class BeanAdapterFactoryImpl implements BeanAdapterFactory
{
	private final ChangeSupportFactory changeSupportFactory;
	
	@Inject
	public BeanAdapterFactoryImpl(ChangeSupportFactory changeSupportFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
	}
	
	@Override
	public BeanAdapter create()
	{
		return new BeanAdapter(changeSupportFactory, new ValueHolder(null, true));
	}
	
	@Override
	public BeanAdapter create(Object bean)
	{
		return new BeanAdapter(changeSupportFactory, new ValueHolder(bean, true));
	}
	
	@Override
	public BeanAdapter create(ValueModel beanChannel)
	{
		return new BeanAdapter(changeSupportFactory, beanChannel);
	}
}

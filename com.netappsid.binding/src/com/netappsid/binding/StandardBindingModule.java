package com.netappsid.binding;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.BeanAdapterFactory;
import com.netappsid.binding.beans.BeanAdapterFactoryImpl;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StandardBindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(ChangeSupportFactory.class).to(StandardChangeSupportFactory.class);
		bind(BeanAdapterFactory.class).to(BeanAdapterFactoryImpl.class);
	}
}

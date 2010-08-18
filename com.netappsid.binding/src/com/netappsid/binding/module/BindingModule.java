package com.netappsid.binding.module;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.BeanAdapterFactory;

public class BindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(BeanAdapterFactory.class);
	}
}

package com.netappsid.binding.module;

import com.google.inject.AbstractModule;
import com.netappsid.binding.DefaultPresentationModelFactory;
import com.netappsid.binding.DynamicPresentationModelFactory;
import com.netappsid.binding.SelectionPresentationModelFactory;
import com.netappsid.binding.beans.BeanAdapterFactory;

public class BindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(DefaultPresentationModelFactory.class);
		bind(DynamicPresentationModelFactory.class);
		bind(SelectionPresentationModelFactory.class);
		bind(BeanAdapterFactory.class);
	}
}

package com.netappsid.binding.module;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.BeanAdapterFactory;
import com.netappsid.binding.presentation.PresentationModelFactory;

public class BindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(PresentationModelFactory.class);
		bind(BeanAdapterFactory.class);
	}
}

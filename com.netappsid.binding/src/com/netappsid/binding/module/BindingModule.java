package com.netappsid.binding.module;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.BeanAdapterFactory;
import com.netappsid.binding.presentation.PresentationModelFactory;
import com.netappsid.binding.selection.SelectionHolder;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.value.ConverterFactory;

public class BindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(StateModel.class);
		bind(SelectionHolder.class);
		bind(ConverterFactory.class);
		bind(PresentationModelFactory.class);
		bind(BeanAdapterFactory.class);
	}
}

package com.netappsid.binding.module;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StandardBindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		install(new BindingModule());
		bind(ChangeSupportFactory.class).to(StandardChangeSupportFactory.class);
	}
}

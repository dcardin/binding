package com.netappsid.binding;

import com.google.inject.AbstractModule;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.beans.support.SwingChangeSupportFactory;

public class SwingBindingModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		install(new BindingModule());
		bind(ChangeSupportFactory.class).to(SwingChangeSupportFactory.class);
	}
}

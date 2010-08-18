package com.netappsid.binding.beans.support;

import java.beans.VetoableChangeSupport;

public class SwingChangeSupportFactory implements ChangeSupportFactory
{
	@Override
	public IdentityPropertyChangeSupport createIdentityPropertyChangeSupport(Object sourceBean)
	{
		return new SwingIdentityPropertyChangeSupport(sourceBean);
	}
	
	@Override
	public VetoableChangeSupport createVetoableChangeSupport(Object sourceBean)
	{
		return new VetoableChangeSupport(sourceBean);
	}
}

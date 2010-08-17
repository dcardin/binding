package com.netappsid.binding.beans.model;

import java.beans.VetoableChangeSupport;

import com.netappsid.binding.beans.support.IdentityPropertyChangeSupport;

public class StandardBeanModel extends BeanModel
{
	private final IdentityPropertyChangeSupport propertyChangeSupport = new IdentityPropertyChangeSupport(this);
	private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);
	
	@Override
	protected final IdentityPropertyChangeSupport getExtendedPropertyChangeSupport()
	{
		return propertyChangeSupport;
	}
	
	@Override
	protected final VetoableChangeSupport getVetoableChangeSupport()
	{
		return vetoableChangeSupport;
	}
}

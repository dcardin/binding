package com.netappsid.binding.beans.model;

import java.beans.VetoableChangeSupport;

import com.netappsid.binding.beans.support.IdentityPropertyChangeSupport;
import com.netappsid.binding.beans.support.SwingIdentityPropertyChangeSupport;

public class SwingBeanModel extends BeanModel
{
	private final IdentityPropertyChangeSupport propertyChangeSupport = new SwingIdentityPropertyChangeSupport(this);
	private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);
	
	@Override
	protected IdentityPropertyChangeSupport getExtendedPropertyChangeSupport()
	{
		return propertyChangeSupport;
	}
	
	@Override
	protected VetoableChangeSupport getVetoableChangeSupport()
	{
		return vetoableChangeSupport;
	}
}

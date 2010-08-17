package com.netappsid.binding.beans;

import java.beans.VetoableChangeSupport;


public class BeanModel extends AbstractBeanModel
{
	@Override
	protected final ExtendedPropertyChangeSupport createExtendedPropertyChangeSupport()
	{
		return new ExtendedPropertyChangeSupport(this);
	}
	
	@Override
	protected final VetoableChangeSupport createVetoableChangeSupport()
	{
		return new VetoableChangeSupport(this);
	}
}

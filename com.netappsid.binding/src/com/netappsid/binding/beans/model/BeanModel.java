package com.netappsid.binding.beans.model;

import java.beans.VetoableChangeSupport;

import com.netappsid.binding.beans.support.ExtendedPropertyChangeSupport;


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

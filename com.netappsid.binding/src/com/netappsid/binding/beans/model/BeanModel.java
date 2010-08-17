package com.netappsid.binding.beans.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.netappsid.binding.BindingUtils;
import com.netappsid.binding.beans.Observable;
import com.netappsid.binding.beans.support.IdentityPropertyChangeSupport;

public abstract class BeanModel implements Observable, Serializable
{
	public final synchronized void addPropertyChangeListener(PropertyChangeListener listener)
	{
		getExtendedPropertyChangeSupport().addPropertyChangeListener(listener);
	}

	public final synchronized void removePropertyChangeListener(PropertyChangeListener listener)
	{
		getExtendedPropertyChangeSupport().removePropertyChangeListener(listener);
	}

	public final synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		getExtendedPropertyChangeSupport().addPropertyChangeListener(propertyName, listener);
	}

	public final synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		getExtendedPropertyChangeSupport().removePropertyChangeListener(propertyName, listener);
	}

	public final synchronized void addVetoableChangeListener(VetoableChangeListener listener)
	{
		getVetoableChangeSupport().addVetoableChangeListener(listener);
	}

	public final synchronized void removeVetoableChangeListener(VetoableChangeListener listener)
	{
		getVetoableChangeSupport().removeVetoableChangeListener(listener);
	}

	public final synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		getVetoableChangeSupport().addVetoableChangeListener(propertyName, listener);
	}

	public final synchronized void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		getVetoableChangeSupport().removeVetoableChangeListener(propertyName, listener);
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners()
	{
		return getExtendedPropertyChangeSupport().getPropertyChangeListeners();
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName)
	{
		return getExtendedPropertyChangeSupport().getPropertyChangeListeners(propertyName);
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners()
	{
		return getVetoableChangeSupport().getVetoableChangeListeners();
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName)
	{
		return getVetoableChangeSupport().getVetoableChangeListeners(propertyName);
	}

	protected final void firePropertyChange(PropertyChangeEvent event)
	{
		getExtendedPropertyChangeSupport().firePropertyChange(event);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		getExtendedPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		if (checkIdentity)
		{
			getExtendedPropertyChangeSupport().fireIdentityPropertyChange(propertyName, oldValue, newValue);
		}
		else
		{
			getExtendedPropertyChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	protected final void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue)
	{
		getExtendedPropertyChangeSupport().fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	protected final void fireVetoableChange(PropertyChangeEvent event) throws PropertyVetoException
	{
		getVetoableChangeSupport().fireVetoableChange(event);
	}

	protected final void fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException
	{
		getVetoableChangeSupport().fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected final boolean equals(Object o1, Object o2)
	{
		return BindingUtils.equals(o1, o2);
	}

	protected abstract IdentityPropertyChangeSupport getExtendedPropertyChangeSupport();

	protected abstract VetoableChangeSupport getVetoableChangeSupport();
}

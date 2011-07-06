package com.netappsid.binding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.jgoodies.binding.BindingUtils;
import com.jgoodies.binding.beans.Observable;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.beans.support.IdentityPropertyChangeSupport;
import com.netappsid.observable.ObservableByName;

public abstract class Bean implements ObservableByName, Serializable
{
	private final IdentityPropertyChangeSupport propertyChangeSupport;
	private final VetoableChangeSupport vetoableChangeSupport;

	public Bean(ChangeSupportFactory changeSupportFactory)
	{
		this.propertyChangeSupport = changeSupportFactory.createIdentityPropertyChangeSupport(this);
		this.vetoableChangeSupport = changeSupportFactory.createVetoableChangeSupport(this);
	}

	@Override
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public synchronized void addVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoableChangeSupport.addVetoableChangeListener(listener);
	}

	public synchronized void removeVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoableChangeSupport.removeVetoableChangeListener(listener);
	}

	public synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		vetoableChangeSupport.addVetoableChangeListener(propertyName, listener);
	}

	public synchronized void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		vetoableChangeSupport.removeVetoableChangeListener(propertyName, listener);
	}

	public synchronized PropertyChangeListener[] getPropertyChangeListeners()
	{
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName)
	{
		return propertyChangeSupport.getPropertyChangeListeners(propertyName);
	}

	public synchronized VetoableChangeListener[] getVetoableChangeListeners()
	{
		return vetoableChangeSupport.getVetoableChangeListeners();
	}

	public synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName)
	{
		return vetoableChangeSupport.getVetoableChangeListeners(propertyName);
	}

	protected void firePropertyChange(PropertyChangeEvent event)
	{
		propertyChangeSupport.firePropertyChange(event);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	@Deprecated
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		if (checkIdentity)
		{
			fireIdentityPropertyChange(propertyName, oldValue, newValue);
		}
		else
		{
			firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	protected void fireIdentityPropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		propertyChangeSupport.fireIdentityPropertyChange(propertyName, oldValue, newValue);
	}

	protected void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue)
	{
		propertyChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	protected void fireVetoableChange(PropertyChangeEvent event) throws PropertyVetoException
	{
		vetoableChangeSupport.fireVetoableChange(event);
	}

	protected void fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException
	{
		vetoableChangeSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected boolean equals(Object o1, Object o2)
	{
		return BindingUtils.equals(o1, o2);
	}
}

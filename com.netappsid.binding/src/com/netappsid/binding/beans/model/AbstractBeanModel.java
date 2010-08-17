package com.netappsid.binding.beans.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import com.netappsid.binding.BindingUtils;
import com.netappsid.binding.beans.Observable;
import com.netappsid.binding.beans.support.ExtendedPropertyChangeSupport;

public abstract class AbstractBeanModel implements Observable, Serializable
{
	private final ExtendedPropertyChangeSupport changeSupport;
	private final VetoableChangeSupport vetoSupport;

	public AbstractBeanModel()
	{
		this.changeSupport = createExtendedPropertyChangeSupport();
		this.vetoSupport = createVetoableChangeSupport();
	}

	public final synchronized void addPropertyChangeListener(PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}

	public final synchronized void removePropertyChangeListener(PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}

	public final synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public final synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public final synchronized void addVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoSupport.addVetoableChangeListener(listener);
	}

	public final synchronized void removeVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoSupport.removeVetoableChangeListener(listener);
	}

	public final synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		vetoSupport.addVetoableChangeListener(propertyName, listener);
	}

	public final synchronized void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener)
	{
		vetoSupport.removeVetoableChangeListener(propertyName, listener);
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners()
	{
		return changeSupport.getPropertyChangeListeners();
	}

	public final synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName)
	{
		return changeSupport.getPropertyChangeListeners(propertyName);
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners()
	{
		return vetoSupport.getVetoableChangeListeners();
	}

	public final synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName)
	{
		return vetoSupport.getVetoableChangeListeners(propertyName);
	}

	protected final void firePropertyChange(PropertyChangeEvent event)
	{
		changeSupport.firePropertyChange(event);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChange(String propertyName, Object oldValue, Object newValue, boolean checkIdentity)
	{
		changeSupport.firePropertyChange(propertyName, oldValue, newValue, checkIdentity);
	}

	protected final void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue)
	{
		changeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
	}

	protected final void fireVetoableChange(PropertyChangeEvent event) throws PropertyVetoException
	{
		vetoSupport.fireVetoableChange(event);
	}

	protected final void fireVetoableChange(String propertyName, Object oldValue, Object newValue) throws PropertyVetoException
	{
		vetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
	}

	protected final boolean equals(Object o1, Object o2)
	{
		return BindingUtils.equals(o1, o2);
	}

	protected abstract ExtendedPropertyChangeSupport createExtendedPropertyChangeSupport();

	protected abstract VetoableChangeSupport createVetoableChangeSupport();
}

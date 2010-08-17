package com.netappsid.binding.beans.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;

public class IdentityPropertyChangeSupport extends PropertyChangeSupport
{
	private final Object source;

	public IdentityPropertyChangeSupport(Object sourceBean)
	{
		super(sourceBean);
		this.source = sourceBean;
	}

	public void fireIdentityPropertyChange(String propertyName, Object oldValue, Object newValue)
	{
		fireIdentityPropertyChange(new PropertyChangeEvent(source, propertyName, oldValue, newValue));
	}

	public void fireIdentityPropertyChange(PropertyChangeEvent evt)
	{
		if (evt.getOldValue() == null || evt.getOldValue() != evt.getNewValue())
		{
			for (PropertyChangeListener listener : getPropertyChangeListeners())
			{
				if (listener instanceof PropertyChangeListenerProxy)
				{
					final PropertyChangeListenerProxy proxy = (PropertyChangeListenerProxy) listener;
					
					if (proxy.getPropertyName().equals(evt.getPropertyName()))
					{
						proxy.propertyChange(evt);
					}
				}
				else
				{
					listener.propertyChange(evt);
				}
			}
		}
	}
}

package com.netappsid.binding.state;

import java.beans.PropertyChangeEvent;

/**
 * Extension to the default PropertyChangeEvent that adds the notion of state management. Allows listeners to be informed if the change is a affecting the bean
 * state or not and take action accordingly.
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * 
 */
public class StatePropertyChangeEvent extends PropertyChangeEvent
{
	private boolean affectState;

	public StatePropertyChangeEvent(Object source, String propertyName, Object oldValue, Object newValue, boolean affectState)
	{
		super(source, propertyName, oldValue, newValue);

		this.affectState = affectState;
	}

	public boolean isAffectingState()
	{
		return affectState;
	}
}

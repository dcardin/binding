package com.netappsid.binding.beans.support;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Assert;
import org.junit.Test;

public class SwingIdentityPropertyChangeSupportTest
{
	@Test
	public void firePropertyChange_EventFiredOnEventDispatchThread()
	{
		final IdentityPropertyChangeSupport support = new SwingIdentityPropertyChangeSupport(new Object());

		support.addPropertyChangeListener(new EventQueuePropertyChangeListenerSpy());
		support.firePropertyChange(null, 1, 2);
	}
	
	@Test
	public void fireIdentityPropertyChange_EventFiredOnEventDispatchThread()
	{
		final IdentityPropertyChangeSupport support = new SwingIdentityPropertyChangeSupport(new Object());

		support.addPropertyChangeListener(new EventQueuePropertyChangeListenerSpy());
		support.fireIdentityPropertyChange(null, 1, 2);
	}
	
	public static class EventQueuePropertyChangeListenerSpy implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			Assert.assertTrue(EventQueue.isDispatchThread());
		}
	}
}

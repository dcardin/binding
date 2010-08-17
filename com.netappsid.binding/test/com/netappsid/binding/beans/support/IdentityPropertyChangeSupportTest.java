package com.netappsid.binding.beans.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class IdentityPropertyChangeSupportTest
{
	@Test
	public void fireIdentityPropertyChange()
	{
		final Object source = new Object();
		final String propertyName = "test";
		final Object oldValue = "old";
		final Object newValue = "new";
		final IdentityPropertyChangeSupport support = new IdentityPropertyChangeSupport(source);
		final PropertyEventListenerSpy eventSpy = new PropertyEventListenerSpy();

		support.addPropertyChangeListener(eventSpy);
		support.fireIdentityPropertyChange(propertyName, oldValue, newValue);

		eventSpy.assertEventRaised(source, propertyName, oldValue, newValue);
	}
	
	@Test
	public void fireIdentityPropertyChange_NoEventIfSameIdentity()
	{
		final Object source = new Object();
		final String propertyName = "test";
		final Object oldValue = "old";
		final Object newValue = oldValue;
		final IdentityPropertyChangeSupport support = new IdentityPropertyChangeSupport(source);
		final PropertyEventListenerSpy eventSpy = new PropertyEventListenerSpy();

		support.addPropertyChangeListener(eventSpy);
		support.fireIdentityPropertyChange(propertyName, oldValue, newValue);

		eventSpy.assertNoEventRaised();
	}
	
	@Test
	public void fireIdentityPropertyChange_EventFiredIfOldValueNull()
	{
		final Object source = new Object();
		final String propertyName = "test";
		final Object oldValue = null;
		final Object newValue = oldValue;
		final IdentityPropertyChangeSupport support = new IdentityPropertyChangeSupport(source);
		final PropertyEventListenerSpy eventSpy = new PropertyEventListenerSpy();

		support.addPropertyChangeListener(eventSpy);
		support.fireIdentityPropertyChange(propertyName, oldValue, newValue);

		eventSpy.assertEventRaised(source, propertyName, oldValue, newValue);
	}

	public static final class PropertyEventListenerSpy implements PropertyChangeListener
	{
		private final List<PropertyChangeEvent> events = new ArrayList<PropertyChangeEvent>();

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			events.add(evt);
		}
		
		public void assertNoEventRaised()
		{
			Assert.assertTrue(events.isEmpty());
		}

		public void assertEventRaised(Object source, String propertyName, Object oldValue, Object newValue)
		{
			for (PropertyChangeEvent event : events)
			{
				if (event.getSource() == source && event.getPropertyName() == propertyName && event.getOldValue() == oldValue
						&& event.getNewValue() == newValue)
				{
					return;
				}
			}
			
			Assert.fail();
		}
	}
}

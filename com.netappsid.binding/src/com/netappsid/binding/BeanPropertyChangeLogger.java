package com.netappsid.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.beans.Observable;

/**
 * Singleton implementation of PropertyChangeListener that logs information about bean property changes in the TRACE channel.
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.1 $
 */
public class BeanPropertyChangeLogger implements PropertyChangeListener
{
	private static final Logger LOGGER = Logger.getLogger(BeanPropertyChangeLogger.class);
	private static PropertyChangeListener instance;	

	private BeanPropertyChangeLogger()
	{
		// Hidden constructor
	}
	
	public static void install(BeanAdapter<?> beanAdapter)
	{
		if (LOGGER.isTraceEnabled() && beanAdapter != null)
		{
			beanAdapter.addBeanPropertyChangeListener(getInstance());
		}
	}
	
	public static void install(Observable bean)
	{
		if (LOGGER.isTraceEnabled() && bean != null)
		{
			((Observable) bean).addPropertyChangeListener(getInstance());
		}
	}
	
	public static void uninstall(BeanAdapter<?> beanAdapter)
	{
		if (beanAdapter != null)
		{
			beanAdapter.removeBeanPropertyChangeListener(getInstance());
		}
	}
	
	public static void uninstall(Observable bean)
	{
		if (bean != null)
		{
			((Observable) bean).removePropertyChangeListener(getInstance());
		}
	}
	
	private static PropertyChangeListener getInstance()
	{
		if (instance == null)
		{
			instance = new BeanPropertyChangeLogger();
		}
		
		return instance;
	}

	public void propertyChange(PropertyChangeEvent evt)
	{
		final String message = String.format("event: { source: %s, property: %s, old: %s, new: %s }", evt.getSource().getClass().getName(), evt
				.getPropertyName(), evt.getOldValue(), evt.getNewValue());

		LOGGER.trace(message);
	}
}
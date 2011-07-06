package com.netappsid.binding.utils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.Observable;
import com.netappsid.binding.beans.BeanAdapter;

/**
 * Singleton implementation of PropertyChangeListener that logs information about bean property changes in the TRACE channel. Please note that this listener is
 * light-weight. It should not store any information about the beans, only register itself and report changes.
 * 
 * TODO Work in progress. Please notify if you want to track something and it doesn't.
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.2 $
 */
public class BeanChangeLogger
{
	private static final Logger LOGGER = Logger.getLogger(BeanChangeLogger.class);
	private static PropertyChangeListener beanPropertyChangeHandler;
	private static PropertyChangeListener beanChangeHandler;

	private BeanChangeLogger()
	{
		// Hidden constructor
	}

	public static void install(BeanAdapter beanAdapter)
	{
		if (LOGGER.isTraceEnabled() && beanAdapter != null)
		{
			beanAdapter.getBeanChannel().addValueChangeListener(getBeanChangeHandler());
			beanAdapter.addBeanPropertyChangeListener(getBeanPropertyChangeHandler());

		}
	}

	public static void install(Observable bean)
	{
		if (LOGGER.isTraceEnabled() && bean != null)
		{
			bean.addPropertyChangeListener(getBeanPropertyChangeHandler());
		}
	}

	public static void uninstall(BeanAdapter beanAdapter)
	{
		if (beanAdapter != null)
		{
			beanAdapter.getBeanChannel().removeValueChangeListener(getBeanChangeHandler());
			beanAdapter.removeBeanPropertyChangeListener(getBeanPropertyChangeHandler());
		}
	}

	public static void uninstall(Observable bean)
	{
		if (bean != null)
		{
			bean.removePropertyChangeListener(getBeanPropertyChangeHandler());
		}
	}

	private static PropertyChangeListener getBeanChangeHandler()
	{
		if (beanChangeHandler == null)
		{
			beanChangeHandler = new BeanChangeHandler();
		}

		return beanChangeHandler;
	}

	private static PropertyChangeListener getBeanPropertyChangeHandler()
	{
		if (beanPropertyChangeHandler == null)
		{
			beanPropertyChangeHandler = new BeanPropertyChangeHandler();
		}

		return beanPropertyChangeHandler;
	}

	private static String getTime()
	{
		return DateFormat.getTimeInstance().format(new Date());
	}

	private static void printToLogger(String message)
	{
		LOGGER.trace(String.format("[%s] %s", getTime(), message));
	}

	private static final class BeanChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			printToLogger(String.format("bean: { source: %s, property: %s, old: %s, new: %s }", evt.getSource().getClass().getName(), evt.getPropertyName(),
					evt.getOldValue(), evt.getNewValue()));
		}
	}

	/**
	 * Logs all property change events in a bean.
	 */
	private static final class BeanPropertyChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			printToLogger(String.format("property: { source: %s, property: %s, old: %s, new: %s }", evt.getSource().getClass().getName(),
					evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()));
		}
	}
}
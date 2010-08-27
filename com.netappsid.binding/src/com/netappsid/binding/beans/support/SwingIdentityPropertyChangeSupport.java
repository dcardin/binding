package com.netappsid.binding.beans.support;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;

import org.apache.log4j.Logger;

public class SwingIdentityPropertyChangeSupport extends IdentityPropertyChangeSupport
{
	private static final Logger LOGGER = Logger.getLogger(SwingIdentityPropertyChangeSupport.class);

	public SwingIdentityPropertyChangeSupport(Object sourceBean)
	{
		super(sourceBean);
	}

	@Override
	public void fireIdentityPropertyChange(final PropertyChangeEvent evt)
	{
		if (EventQueue.isDispatchThread())
		{
			super.fireIdentityPropertyChange(evt);
		}
		else
		{
			try
			{
				EventQueue.invokeAndWait(new Runnable()
					{
						@Override
						public void run()
						{
							fireIdentityPropertyChange(evt);
						}
					});
			}
			catch (Exception e)
			{
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void firePropertyChange(final PropertyChangeEvent evt)
	{
		if (EventQueue.isDispatchThread())
		{
			super.firePropertyChange(evt);
		}
		else
		{
			try
			{
				EventQueue.invokeAndWait(new Runnable()
					{
						@Override
						public void run()
						{
							firePropertyChange(evt);
						}
					});
			}
			catch (Exception e)
			{
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}

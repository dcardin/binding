package com.netappsid.binding.beans.support;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;


public class SwingIdentityPropertyChangeSupport extends IdentityPropertyChangeSupport
{
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
			EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						fireIdentityPropertyChange(evt);
					}
				});
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
			EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						firePropertyChange(evt);
					}
				});
		}
	}
}

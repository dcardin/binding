package com.netappsid.binding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

public final class BufferedValueModel extends AbstractValueModel
{
	public static final String PROPERTYNAME_BUFFERING = "buffering";
	public static final String PROPERTYNAME_SUBJECT = "subject";
	public static final String PROPERTYNAME_TRIGGER_CHANNEL = "triggerChannel";

	private final ValueChangeHandler valueChangeHandler;
	private final TriggerChangeHandler triggerChangeHandler;

	private ValueModel subject;
	private ValueModel triggerChannel;
	private Object bufferedValue;
	private boolean valueAssigned;

	public BufferedValueModel(ChangeSupportFactory changeSupportFactory, ValueModel subject, ValueModel triggerChannel)
	{
		super(changeSupportFactory);
		valueChangeHandler = new ValueChangeHandler();
		triggerChangeHandler = new TriggerChangeHandler();
		setSubject(subject);
		setTriggerChannel(triggerChannel);
		setBuffering(false);
	}

	public ValueModel getSubject()
	{
		return subject;
	}

	public void setSubject(ValueModel newSubject)
	{
		ValueModel oldSubject = getSubject();
		ReadAccessResult oldReadValue = readBufferedOrSubjectValue();
		Object oldValue = oldReadValue.value;
		if (oldSubject != null)
		{
			oldSubject.removeValueChangeListener(valueChangeHandler);
		}
		subject = newSubject;
		if (newSubject != null)
		{
			newSubject.addValueChangeListener(valueChangeHandler);
		}
		firePropertyChange(PROPERTYNAME_SUBJECT, oldSubject, newSubject);
		if (isBuffering())
			return;

		ReadAccessResult newReadValue = readBufferedOrSubjectValue();
		Object newValue = newReadValue.value;
		
		if (oldValue != null || newValue != null)
		{
			fireValueChange(oldValue, newValue, true);
		}
	}

	public ValueModel getTriggerChannel()
	{
		return triggerChannel;
	}

	public void setTriggerChannel(ValueModel newTriggerChannel)
	{
		if (newTriggerChannel == null)
			throw new NullPointerException("The trigger channel must not be null.");

		ValueModel oldTriggerChannel = getTriggerChannel();
		if (oldTriggerChannel != null)
		{
			oldTriggerChannel.removeValueChangeListener(triggerChangeHandler);
		}
		triggerChannel = newTriggerChannel;
		newTriggerChannel.addValueChangeListener(triggerChangeHandler);
		firePropertyChange(PROPERTYNAME_TRIGGER_CHANNEL, oldTriggerChannel, newTriggerChannel);
	}

	public Object getValue()
	{
		if (subject == null)
			throw new NullPointerException("The subject must not be null " + "when reading a value from a BufferedValueModel.");

		return isBuffering() ? bufferedValue : subject.getValue();
	}

	public void setValue(Object newBufferedValue)
	{
		if (subject == null)
			throw new NullPointerException("The subject must not be null " + "when setting a value to a BufferedValueModel.");

		ReadAccessResult oldReadValue = readBufferedOrSubjectValue();
		Object oldValue = oldReadValue.value;
		bufferedValue = newBufferedValue;
		setBuffering(true);
		if (oldReadValue.readable && oldValue == newBufferedValue)
			return;
		fireValueChange(oldValue, newBufferedValue, true);
	}

	private ReadAccessResult readBufferedOrSubjectValue()
	{
		try
		{
			Object value = getValue(); // May fail with write-only models
			return new ReadAccessResult(value, true);
		}
		catch (Exception e)
		{
			return new ReadAccessResult(null, false);
		}
	}

	public void release()
	{
		ValueModel aSubject = getSubject();
		if (aSubject != null)
		{
			aSubject.removeValueChangeListener(valueChangeHandler);
		}
		ValueModel aTriggerChannel = getTriggerChannel();
		if (aTriggerChannel != null)
		{
			aTriggerChannel.removeValueChangeListener(triggerChangeHandler);
		}
	}

	public boolean isBuffering()
	{
		return valueAssigned;
	}

	private void setBuffering(boolean newValue)
	{
		boolean oldValue = isBuffering();
		valueAssigned = newValue;
		firePropertyChange(PROPERTYNAME_BUFFERING, oldValue, newValue);
	}

	private void commit()
	{
		if (isBuffering())
		{
			setBuffering(false);
			valueChangeHandler.oldValue = bufferedValue;
			subject.setValue(bufferedValue);
			valueChangeHandler.oldValue = null;
		}
		else if (subject == null)
			throw new NullPointerException("The subject must not be null " + "while committing a value in a BufferedValueModel.");
	}

	private void flush()
	{
		Object oldValue = getValue();
		setBuffering(false);
		Object newValue = getValue();
		fireValueChange(oldValue, newValue, true);
	}

	@Override
	protected String paramString()
	{
		return "value=" + valueString() + "; buffering" + isBuffering();
	}

	private static final class ReadAccessResult
	{
		final Object value;
		final boolean readable;

		private ReadAccessResult(Object value, boolean readable)
		{
			this.value = value;
			this.readable = readable;
		}
	}

	private final class ValueChangeHandler implements PropertyChangeListener
	{
		Object oldValue;

		public void propertyChange(PropertyChangeEvent evt)
		{
			if (!isBuffering())
			{
				fireValueChange(oldValue != null ? oldValue : evt.getOldValue(), evt.getNewValue(), true);
			}
		}
	}

	private final class TriggerChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (Boolean.TRUE.equals(evt.getNewValue()))
				commit();
			else if (Boolean.FALSE.equals(evt.getNewValue()))
				flush();
		}
	}
}

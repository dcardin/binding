package com.netappsid.binding.value;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

public final class ComponentValueModel extends AbstractValueModel
{
	public static final String PROPERTYNAME_ENABLED = "enabled";
	public static final String PROPERTYNAME_VISIBLE = "visible";
	public static final String PROPERTYNAME_EDITABLE = "editable";

	private final ValueModel subject;
	private boolean enabled;
	private boolean visible;
	private boolean editable;

	public ComponentValueModel(ChangeSupportFactory changeSupportFactory, ValueModel subject)
	{
		super(changeSupportFactory);
		this.subject = subject;
		this.enabled = true;
		this.editable = true;
		this.visible = true;
		subject.addValueChangeListener(new SubjectValueChangeHandler());
	}

	public Object getValue()
	{
		return subject.getValue();
	}

	public void setValue(Object newValue)
	{
		subject.setValue(newValue);
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean b)
	{
		boolean oldEnabled = isEnabled();
		enabled = b;
		firePropertyChange(PROPERTYNAME_ENABLED, oldEnabled, b);
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean b)
	{
		boolean oldVisible = isVisible();
		visible = b;
		firePropertyChange(PROPERTYNAME_VISIBLE, oldVisible, b);
	}

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean b)
	{
		boolean oldEditable = isEditable();
		editable = b;
		firePropertyChange(PROPERTYNAME_EDITABLE, oldEditable, b);
	}

	private final class SubjectValueChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			fireValueChange(evt.getOldValue(), evt.getNewValue(), true);
		}
	}
}

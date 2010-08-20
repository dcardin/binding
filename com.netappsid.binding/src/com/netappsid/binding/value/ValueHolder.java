package com.netappsid.binding.value;

import com.netappsid.binding.beans.support.ChangeSupportFactory;

public final class ValueHolder extends AbstractValueModel
{
	private Object value;
	private boolean checkIdentity;

	public ValueHolder(ChangeSupportFactory changeSupportFactory)
	{
		this(changeSupportFactory, null);
	}
	
	public ValueHolder(ChangeSupportFactory changeSupportFactory, Object initialValue)
	{
		this(changeSupportFactory, initialValue, false);
	}

	public ValueHolder(ChangeSupportFactory changeSupportFactory, Object initialValue, boolean checkIdentity)
	{
		super(changeSupportFactory);
		this.value = initialValue;
		this.checkIdentity = checkIdentity;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object newValue)
	{
		setValue(newValue, isIdentityCheckEnabled());
	}

	public boolean isIdentityCheckEnabled()
	{
		return checkIdentity;
	}

	public void setIdentityCheckEnabled(boolean checkIdentity)
	{
		this.checkIdentity = checkIdentity;
	}

	public void setValue(Object newValue, boolean checkIdentity)
	{
		Object oldValue = getValue();
		if (oldValue == newValue)
			return;
		value = newValue;
		fireValueChange(oldValue, newValue, checkIdentity);
	}
}

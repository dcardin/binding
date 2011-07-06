/*
 * Copyright (c) 2002-2008 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.netappsid.binding.value;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.Bean;
import com.netappsid.binding.beans.support.ChangeSupportFactory;

/**
 * An abstract class that minimizes the effort required to implement the {@link ValueModel} interface. It provides convenience methods to convert boolean,
 * double, float, int, and long to their corresponding Object values and vice versa.
 * <p>
 * 
 * Subclasses must implement <code>getValue()</code> and <code>setValue(Object)</code> to get and set this model's value.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.10 $
 * 
 */

public abstract class AbstractValueModel extends Bean implements ValueModel
{

	/**
	 * The name of the bound property <em>value</em>.
	 */
	public static String PROPERTYNAME_VALUE = "value";

	// Change Management ****************************************************

	public AbstractValueModel(ChangeSupportFactory changeSupportFactory)
	{
		super(changeSupportFactory);
	}
	
	/**
	 * Registers the given PropertyChangeListener with this model. The listener will be notified if the value has changed.
	 * <p>
	 * 
	 * The PropertyChangeEvents delivered to the listener have the name set to "value". In other words, the listeners won't get notified when a
	 * PropertyChangeEvent is fired that has a null object as the name to indicate an arbitrary set of the event source's properties have changed.
	 * <p>
	 * 
	 * In the rare case, where you want to notify a PropertyChangeListener even with PropertyChangeEvents that have no property name set, you can register the
	 * listener with #addPropertyChangeListener, not #addValueChangeListener.
	 * 
	 * @param l
	 *            the listener to add
	 * 
	 * @see ValueModel
	 */
	@Override
	public void addValueChangeListener(PropertyChangeListener l)
	{
		addPropertyChangeListener(PROPERTYNAME_VALUE, l);
	}

	/**
	 * Removes the given PropertyChangeListener from the model.
	 * 
	 * @param l
	 *            the listener to remove
	 */
	@Override
	public void removeValueChangeListener(PropertyChangeListener l)
	{
		removePropertyChangeListener(PROPERTYNAME_VALUE, l);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the value before the change
	 * @param newValue
	 *            the value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(Object oldValue, Object newValue)
	{
		firePropertyChange(PROPERTYNAME_VALUE, oldValue, newValue);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * <p>
	 * 
	 * The boolean parameter specifies whether differences between the old and new value are tested using <code>==</code> or <code>#equals</code>.
	 * 
	 * @param oldValue
	 *            the value before the change
	 * @param newValue
	 *            the value after the change
	 * @param checkIdentity
	 *            true to compare the old and new value using <code>==</code>, false to use <code>#equals</code>
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(Object oldValue, Object newValue, boolean checkIdentity)
	{
		if (checkIdentity)
		{
			fireIdentityPropertyChange(PROPERTYNAME_VALUE, oldValue, newValue);
		}
		else
		{
			firePropertyChange(PROPERTYNAME_VALUE, oldValue, newValue);
		}
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the boolean value before the change
	 * @param newValue
	 *            the boolean value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(boolean oldValue, boolean newValue)
	{
		fireValueChange(Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the int value before the change
	 * @param newValue
	 *            the int value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(int oldValue, int newValue)
	{
		fireValueChange(Integer.valueOf(oldValue), Integer.valueOf(newValue));
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the long value before the change
	 * @param newValue
	 *            the long value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(long oldValue, long newValue)
	{
		fireValueChange(Long.valueOf(oldValue), Long.valueOf(newValue));
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the double value before the change
	 * @param newValue
	 *            the double value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(double oldValue, double newValue)
	{
		fireValueChange(Double.valueOf(oldValue), Double.valueOf(newValue));
	}

	/**
	 * Notifies all listeners that have registered interest for notification on this event type. The event instance is lazily created using the parameters
	 * passed into the fire method.
	 * 
	 * @param oldValue
	 *            the float value before the change
	 * @param newValue
	 *            the float value after the change
	 * 
	 * @see java.beans.PropertyChangeSupport
	 */
	public void fireValueChange(float oldValue, float newValue)
	{
		fireValueChange(Float.valueOf(oldValue), Float.valueOf(newValue));
	}

	// Type Conversion ******************************************************

	/**
	 * Converts this model's value and returns it as a <code>boolean</code>.
	 * 
	 * @return the <code>boolean</code> value
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>Boolean</code>
	 * @throws NullPointerException
	 *             if the value is {@code null}
	 */
	public boolean booleanValue()
	{
		return ((Boolean) getValue()).booleanValue();
	}

	/**
	 * Converts this model's value and returns it as a <code>double</code>.
	 * 
	 * @return the <code>double</code> value
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>Double</code>
	 * @throws NullPointerException
	 *             if the value is {@code null}
	 */
	public double doubleValue()
	{
		return ((Double) getValue()).doubleValue();
	}

	/**
	 * Converts this model's value and returns it as a <code>float</code>.
	 * 
	 * @return the <code>float</code> value
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>Float</code>
	 * @throws NullPointerException
	 *             if the value is {@code null}
	 */
	public float floatValue()
	{
		return ((Float) getValue()).floatValue();
	}

	/**
	 * Converts this model's value and returns it as an <code>int</code>.
	 * 
	 * @return the <code>int</code> value
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>Integer</code>
	 * @throws NullPointerException
	 *             if the value is {@code null}
	 */
	public int intValue()
	{
		return ((Integer) getValue()).intValue();
	}

	/**
	 * Converts this model's value and returns it as a <code>long</code>.
	 * 
	 * @return the <code>long</code> value
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>Long</code>
	 * @throws NullPointerException
	 *             if the value is {@code null}
	 */
	public long longValue()
	{
		return ((Long) getValue()).longValue();
	}

	/**
	 * Converts this model's value and returns it as a <code>String</code>.
	 * 
	 * @return this model's value as String
	 * @throws ClassCastException
	 *             if the observed value is not of type <code>String</code>
	 */
	public String getString()
	{
		return (String) getValue();
	}

	/**
	 * Converts the given <code>boolean</code> to a <code>Boolean</code> and sets it as new value.
	 * 
	 * @param b
	 *            the value to be converted and set as new value
	 */
	public void setValue(boolean b)
	{
		setValue(Boolean.valueOf(b));
	}

	/**
	 * Converts the given <code>double</code> to a <code>Double</code> and sets it as new value.
	 * 
	 * @param d
	 *            the value to be converted and set as new value
	 */
	public void setValue(double d)
	{
		setValue(Double.valueOf(d));
	}

	/**
	 * Converts the given <code>float</code> to a <code>Float</code> and sets it as new value.
	 * 
	 * @param f
	 *            the value to be converted and set as new value
	 */
	public void setValue(float f)
	{
		setValue(Float.valueOf(f));
	}

	/**
	 * Converts the given <code>int</code> to an <code>Integer</code> and sets it as new value.
	 * 
	 * @param i
	 *            the value to be converted and set as new value
	 */
	public void setValue(int i)
	{
		setValue(Integer.valueOf(i));
	}

	/**
	 * Converts the given <code>long</code> to a <code>Long</code> and sets it as new value.
	 * 
	 * @param l
	 *            the value to be converted and set as new value
	 */
	public void setValue(long l)
	{
		setValue(Long.valueOf(l));
	}

	/**
	 * Returns a string representation of this value model. Answers the print string of the observed value.
	 * 
	 * @return a string representation of this value model
	 */
	@Override
	public String toString()
	{
		return getClass().getName() + "[" + paramString() + "]";
	}

	/**
	 * Returns a string representing the state of this model. This method is intended to be used only for debugging purposes, and the content and format of the
	 * returned string may vary between implementations. The returned string may be empty but may not be {@code null}.
	 * 
	 * @return a string representation of this model's state
	 * @since 2.0.3
	 */
	protected String paramString()
	{
		return "value=" + valueString();
	}

	/**
	 * Returns a string representing the value of this model. This method is intended to be used for debugging purposes only.
	 * 
	 * @return a string representation of this model's value
	 * @since 2.0.3
	 */
	protected String valueString()
	{
		try
		{
			Object value = getValue();
			return value == null ? "null" : value.toString();
		}
		catch (Exception e)
		{
			return "Can't read";
		}
	}

}

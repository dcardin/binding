package com.netappsid.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

/**
 * 
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("serial")
public class DefaultPresentationModel extends PresentationModel
{
	public static final String PROPERTYNAME_BEAN = "bean";

	private BeanAdapter<Object> beanAdapter;

	public DefaultPresentationModel(Class<?> beanClass)
	{
		this(beanClass, new ValueHolder(null, true));
	}

	public DefaultPresentationModel(Class<?> beanClass, Object bean)
	{
		this(beanClass, new ValueHolder(bean, true));
	}

	public DefaultPresentationModel(Class<?> beanClass, ValueModel beanChannel)
	{
		this.beanAdapter = new BeanAdapter<Object>(beanChannel, true);
		
		setBeanClass(beanClass);
		beanAdapter.addPropertyChangeListener(BeanAdapter.PROPERTYNAME_BEAN, new BeanChangeHandler());
	}
	
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(listener);
	}

	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(propertyName, listener);
	}

	public Object getBean()
	{
		return beanAdapter.getBean();
	}

	public ValueModel getBeanChannel()
	{
		return beanAdapter.getBeanChannel();
	}

	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return beanAdapter.getBeanPropertyChangeListeners();
	}

	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return beanAdapter.getBeanPropertyChangeListeners(propertyName);
	}

	public Object getValue(String propertyName)
	{
		return getValueModel(propertyName).getValue();
	}

	public ValueModel getValueModel(String propertyName)
	{		
		ValueModel valueModel = null;
		int index = propertyName.lastIndexOf('.');

		if (index == -1)
		{
			valueModel = beanAdapter.getValueModel(propertyName);
		}
		else
		{
			valueModel = getSubModel(propertyName.substring(0, index)).getValueModel(propertyName.substring(index + 1, propertyName.length()));
		}

		return valueModel;
	}

	public ValueModel getValueModel(String propertyName, String getterName, String setterName)
	{
		ValueModel valueModel = null;
		int index = propertyName.lastIndexOf('.');

		if (index == -1)
		{
			valueModel = beanAdapter.getValueModel(propertyName, getterName, setterName);
		}
		else
		{
			valueModel = getSubModel(propertyName.substring(0, index)).getValueModel(propertyName.substring(index + 1, propertyName.length()), getterName,
					setterName);
		}

		return valueModel;
	}

	public void releaseBeanListeners()
	{
		beanAdapter.release();
	}

	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(listener);
	}

	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(propertyName, listener);
	}

	public void setBean(Object newBean)
	{
		beanAdapter.setBean(newBean);
	}

	public void setValue(String propertyName, Object newValue)
	{
		getValueModel(propertyName).setValue(newValue);
	}

	/**
	 * Responsible for bubbling bean change events to listeners on the PresentationModel.
	 * 
	 * @author Eric Belanger
	 * @author NetAppsID Inc.
	 * @version $Revision: 1.1 $
	 */
	private final class BeanChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			firePropertyChange(PROPERTYNAME_BEAN, evt.getOldValue(), evt.getNewValue(), true);
		}
	}

}

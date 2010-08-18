package com.netappsid.binding.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.netappsid.binding.beans.exception.PropertyUnboundException;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.beans.support.IndirectPropertyChangeSupport;
import com.netappsid.binding.beans.support.StandardChangeSupportFactory;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;
import com.netappsid.validate.Validate;

public class BeanAdapter extends Bean
{
	private final ValueModel beanChannel;
	private final Map<String, BeanPropertyValueModel> propertyAdapters;
	private final IndirectPropertyChangeSupport indirectChangeSupport;
	private final PropertyChangeListener propertyChangeHandler;

	private Object storedOldBean;

	public BeanAdapter()
	{
		this(new StandardChangeSupportFactory());
	}
	
	public BeanAdapter(ChangeSupportFactory changeSupportFactory)
	{
		this(changeSupportFactory, (ValueModel) null);
	}
	
	public BeanAdapter(Object bean)
	{
		this(new StandardChangeSupportFactory(), bean);
	}
	
	public BeanAdapter(ChangeSupportFactory changeSupportFactory, Object bean)
	{
		this(changeSupportFactory, new ValueHolder(bean, true));
	}
	
	public BeanAdapter(ValueModel beanChannel)
	{
		this(new StandardChangeSupportFactory(), beanChannel);
	}

	public BeanAdapter(ChangeSupportFactory changeSupportFactory, ValueModel beanChannel)
	{
		super(changeSupportFactory);
		
		this.beanChannel = beanChannel != null ? beanChannel : new ValueHolder(null, true);
		this.propertyAdapters = new HashMap<String, BeanPropertyValueModel>();
		this.indirectChangeSupport = new IndirectPropertyChangeSupport(this.beanChannel);
		this.propertyChangeHandler = new PropertyChangeHandler();
		this.beanChannel.addValueChangeListener(new BeanChangeHandler());
		this.storedOldBean = getBean();

		checkBeanChannelIdentityCheck(this.beanChannel);
		addChangeHandlerTo(getBean());
	}

	public ValueModel getBeanChannel()
	{
		return beanChannel;
	}

	public Object getBean()
	{
		return beanChannel.getValue();
	}

	public void setBean(Object newBean)
	{
		beanChannel.setValue(newBean);
	}

	public Object getValue(String propertyName)
	{
		return getValueModel(propertyName).getValue();
	}

	public void setValue(String propertyName, Object newValue)
	{
		getValueModel(propertyName).setValue(newValue);
	}

	public BeanPropertyValueModel getValueModel(String propertyName)
	{
		Validate.notNull(propertyName, "The property name must not be null.");

		final BeanPropertyValueModel registeredPropertyAdapter = propertyAdapters.get(propertyName);

		if (registeredPropertyAdapter == null)
		{
			propertyAdapters.put(propertyName, new BeanPropertyValueModel(getBeanChannel(), propertyName));
		}

		return propertyAdapters.get(propertyName);
	}

	public synchronized void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		if (listener != null)
		{
			indirectChangeSupport.addPropertyChangeListener(listener);
		}
	}

	public synchronized void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		if (listener != null)
		{
			indirectChangeSupport.removePropertyChangeListener(listener);
		}
	}

	public synchronized void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		if (listener != null)
		{
			indirectChangeSupport.addPropertyChangeListener(propertyName, listener);
		}
	}

	public synchronized void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		if (listener != null)
		{
			indirectChangeSupport.removePropertyChangeListener(propertyName, listener);
		}
	}

	public synchronized PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return indirectChangeSupport.getPropertyChangeListeners();
	}

	public synchronized PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return indirectChangeSupport.getPropertyChangeListeners(propertyName);
	}

	public synchronized void release()
	{
		removeChangeHandlerFrom(getBean());
		indirectChangeSupport.removeAll();
	}

	private void addChangeHandlerTo(Object bean)
	{
		if (bean != null)
		{
			if (BeanUtils.supportsBoundProperties(bean.getClass()))
			{
				BeanUtils.addPropertyChangeListener(bean, bean.getClass(), propertyChangeHandler);
			}
			else
			{
				throw new PropertyUnboundException(
						"The bean must provide support for listening on property changes as described in section 7.4.5 of the Java Bean Specification.");
			}
		}
	}

	private void removeChangeHandlerFrom(Object bean)
	{
		if (bean != null)
		{
			BeanUtils.removePropertyChangeListener(bean, bean.getClass(), propertyChangeHandler);
		}
	}

	private void checkBeanChannelIdentityCheck(ValueModel valueModel)
	{
		if (valueModel instanceof ValueHolder && !((ValueHolder) valueModel).isIdentityCheckEnabled())
		{
			throw new IllegalArgumentException("The bean channel must have the identity check enabled.");
		}
	}

	private final class BeanChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			final Object newBean = evt.getNewValue() != null ? evt.getNewValue() : getBean();

			setBean(storedOldBean, newBean);
			storedOldBean = newBean;
		}

		private void setBean(Object oldBean, Object newBean)
		{
			fireIdentityPropertyChange(PROPERTYNAME_BEFORE_BEAN, oldBean, newBean);
			removeChangeHandlerFrom(oldBean);
			forwardAllAdaptedValuesChanged(oldBean, newBean);
			addChangeHandlerTo(newBean);
			fireIdentityPropertyChange(PROPERTYNAME_BEAN, oldBean, newBean);
			fireIdentityPropertyChange(PROPERTYNAME_AFTER_BEAN, oldBean, newBean);
		}

		private void forwardAllAdaptedValuesChanged(Object oldBean, Object newBean)
		{
			for (Object adapter : propertyAdapters.values().toArray())
			{
				((BeanPropertyValueModel) adapter).setBean(oldBean, newBean);
			}
		}
	}

	private final class PropertyChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getPropertyName() == null)
			{
				forwardAllAdaptedValuesChanged();
			}
			else
			{
				final BeanPropertyValueModel adapter = propertyAdapters.get(evt.getPropertyName());

				if (adapter != null)
				{
					adapter.fireValueChange(evt.getOldValue(), evt.getNewValue(), true);
				}
			}
		}

		private void forwardAllAdaptedValuesChanged()
		{
			final Object currentBean = getBean();

			for (Object adapter : propertyAdapters.values().toArray())
			{
				((BeanPropertyValueModel) adapter).fireChange(currentBean);
			}
		}
	}

	public static final String PROPERTYNAME_BEFORE_BEAN = "beforeBean";
	public static final String PROPERTYNAME_BEAN = "bean";
	public static final String PROPERTYNAME_AFTER_BEAN = "afterBean";
}

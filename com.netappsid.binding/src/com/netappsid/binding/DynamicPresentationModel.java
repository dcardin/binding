package com.netappsid.binding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.netappsid.validate.Validate;

@SuppressWarnings("serial")
public class DynamicPresentationModel extends PresentationModel
{
	private ValueModel mapChannel;
	private PropertyChangeSupport propertyChangeSupport;
	private Map<String, ValueModel> valueModels;

	public DynamicPresentationModel(ValueModel mapChannel)
	{
		this.mapChannel = Validate.notNull(mapChannel, "Map Channel cannot be null.");
		this.propertyChangeSupport = new PropertyChangeSupport(mapChannel);
		
		setBeanClass(Map.class);
	}

	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public Object getBean()
	{
		return getBeanChannel().getValue();
	}

	public ValueModel getBeanChannel()
	{
		return mapChannel;
	}

	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return propertyChangeSupport.getPropertyChangeListeners(propertyName);
	}

	public PresentationModel getSubModel(String propertyName)
	{
		PresentationModel subModel = null;
		int index = propertyName.indexOf('.');
		
		if (index == -1)
		{
			subModel = getSubModels().get(propertyName);
			
			if (subModel == null)
			{
				subModel = PresentationModelFactory.createPresentationModel(this, propertyName);
				getSubModels().put(propertyName, subModel);
			}
		}
		else
		{
			subModel = getSubModels().get(propertyName.substring(0, index));
			
			if (subModel == null)
			{
				subModel = PresentationModelFactory.createPresentationModel(this, propertyName.substring(0, index));
				getSubModels().put(propertyName.substring(0, index), subModel);
			}
			
			subModel = subModel.getSubModel(propertyName.substring(index + 1, propertyName.length()));
		}
		
		return subModel;
	}

	public Object getValue(String propertyName)
	{
		return getValueModel(propertyName).getValue();
	}

	public ValueModel getValueModel(String propertyName)
	{
		if (getBean() == null)
		{
			setBean(new HashMap<String, Object>());
		}
		
		return getValueModels().containsKey(propertyName) ? getValueModels().get(propertyName) : registerValueModel(propertyName);
	}
	
	public ValueModel getValueModel(String propertyName, String getterName, String setterName)
	{
		throw new UnsupportedOperationException();
	}

	public void releaseBeanListeners()
	{
		if (getPropertyChangeListeners() != null)
		{
			for (PropertyChangeListener listener : getPropertyChangeListeners())
			{
				if (listener instanceof PropertyChangeListenerProxy)
				{
					removePropertyChangeListener(((PropertyChangeListenerProxy) listener).getPropertyName(), listener);
				}
				else
				{
					removePropertyChangeListener(listener);
				}
			}
		}
	}

	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	public void setBean(Object newBean)
	{
		if (newBean != null && !(newBean instanceof Map<?, ?>))
		{
			throw new IllegalArgumentException("Can only set a Map or a null value for the new bean.");
		}

		getBeanChannel().setValue(newBean);
	}

	public void setValue(String propertyName, Object newValue)
	{
		getValueModel(propertyName).setValue(newValue);
	}

	private Map<String, ValueModel> getValueModels()
	{
		if (valueModels == null)
		{
			valueModels = new HashMap<String, ValueModel>();
		}

		return valueModels;
	}

	@SuppressWarnings("unchecked")
	private ValueModel registerValueModel(String propertyName)
	{
		ValueModel valueModel;
		
		if (!((Map) getBean()).containsKey(propertyName))
		{
			((Map) getBean()).put(propertyName, (Object) null);
		}
		
		valueModel = new ValueHolder(((Map) getBean()).get(propertyName));
		getValueModels().put(propertyName, valueModel);
		
		return valueModel;
	}
}

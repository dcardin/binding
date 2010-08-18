package com.netappsid.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;

@SuppressWarnings("serial")
public class DynamicPresentationModel extends PresentationModel
{
	private final PropertyChangeListener mappedValueChangeHandler = new MappedValueChangeHandler();

	private ValueModel mapChannel;
	private PropertyChangeSupport propertyChangeSupport;
	private Map<String, ValueModel> valueModels;
	private Map<ValueModel, String> valueModelNames;

	public DynamicPresentationModel(ChangeSupportFactory changeSupportFactory, ValueModel mapChannel)
	{
		super(changeSupportFactory);
		
		this.mapChannel = mapChannel;
		this.propertyChangeSupport = changeSupportFactory.createIdentityPropertyChangeSupport(mapChannel);

		setBeanClass(Map.class);
		mapChannel.addValueChangeListener(new MapChangeHandler());
	}

	@Override
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public Object getBean()
	{
		return getBeanChannel().getValue();
	}

	@Override
	public ValueModel getBeanChannel()
	{
		return mapChannel;
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return propertyChangeSupport.getPropertyChangeListeners(propertyName);
	}

	@Override
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

	@Override
	public Object getValue(String propertyName)
	{
		return getValueModel(propertyName).getValue();
	}

	@Override
	public ValueModel getValueModel(String propertyName)
	{
		if (getBean() == null)
		{
			setBean(new HashMap<String, Object>());
		}

		return getValueModels().containsKey(propertyName) ? getValueModels().get(propertyName) : registerValueModel(propertyName);
	}

	@Override
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

	@Override
	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public void setBean(Object newBean)
	{
		if (newBean != null && !(newBean instanceof Map<?, ?>))
		{
			throw new IllegalArgumentException("Can only set a Map or a null value for the new bean.");
		}

		getBeanChannel().setValue(newBean);
	}

	@Override
	public void setValue(String propertyName, Object newValue)
	{
		getValueModel(propertyName).setValue(newValue);
	}

	@Override
	public StateModel getStateModel()
	{
		return null;
	}

	private Map<ValueModel, String> getValueModelNames()
	{
		if (valueModelNames == null)
		{
			valueModelNames = new HashMap<ValueModel, String>();
		}

		return valueModelNames;
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
		valueModel.addValueChangeListener(mappedValueChangeHandler);
		getValueModels().put(propertyName, valueModel);
		getValueModelNames().put(valueModel, propertyName);

		return valueModel;
	}

	private final class MapChangeHandler implements PropertyChangeListener
	{
		@SuppressWarnings("unchecked")
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt.getNewValue() instanceof Map)
			{
				Map newMap = (Map) evt.getNewValue();

				for (Entry<String, ValueModel> entry : getValueModels().entrySet())
				{
					if (newMap.containsKey(entry.getKey()))
					{
						entry.getValue().setValue(newMap.get(entry.getKey()));
					}
					else
					{
						newMap.put(entry.getKey(), entry.getValue().getValue());
					}
				}
			}
		}
	}

	private final class MappedValueChangeHandler implements PropertyChangeListener
	{
		@SuppressWarnings("unchecked")
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (getValueModelNames().containsKey(evt.getSource()))
			{
				((Map) getBean()).put(getValueModelNames().get(evt.getSource()), evt.getNewValue());
			}
		}
	}
}

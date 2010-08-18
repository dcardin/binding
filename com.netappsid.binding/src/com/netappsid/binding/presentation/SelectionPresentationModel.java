package com.netappsid.binding.presentation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.selection.SelectionHolder;
import com.netappsid.binding.selection.SelectionModel;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.value.ValueModel;
import com.netappsid.validate.Validate;

/**
 * 
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.2 $
 */
@SuppressWarnings("serial")
public class SelectionPresentationModel extends PresentationModel
{
	public static final String DEFAULT_SELECTION = "selected";
	public static final String PROPERTYNAME_BEAN_LIST = "beanList";

	private final PresentationModelFactory presentationModelFactory;

	private ValueModel beanListChannel;
	private Map<String, SelectionModel> selectionModels;

	protected SelectionPresentationModel(PresentationModelFactory presentationModelFactory, ChangeSupportFactory changeSupportFactory, Class<?> beanClass,
			ValueModel beanListChannel)
	{
		super(changeSupportFactory);

		this.presentationModelFactory = presentationModelFactory;
		this.beanListChannel = beanListChannel;
		setBeanClass(beanClass);
	}

	@Override
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		getSubModel(DEFAULT_SELECTION).addBeanPropertyChangeListener(listener);
	}

	@Override
	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		int index = propertyName.indexOf('.');

		if (index != -1)
		{
			getSubModel(propertyName.substring(0, index)).addBeanPropertyChangeListener(propertyName.substring(index + 1, propertyName.length()), listener);
		}
		else
		{
			throw new IllegalArgumentException("Property name must start with a selection key.");
		}
	}

	@Override
	public Object getBean()
	{
		return getSelectedBean(DEFAULT_SELECTION);
	}

	@Override
	public ValueModel getBeanChannel()
	{
		return getSelectedBeanChannel(DEFAULT_SELECTION);
	}

	public List<?> getBeanList()
	{
		return (List<?>) getBeanListChannel().getValue();
	}

	public ValueModel getBeanListChannel()
	{
		return beanListChannel;
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return getSelectedBeanPropertyChangeListeners(DEFAULT_SELECTION);
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		int index = propertyName.indexOf('.');

		if (index != -1)
		{
			return getSubModel(propertyName.substring(0, index)).getBeanPropertyChangeListeners(propertyName.substring(index + 1, propertyName.length()));
		}
		else
		{
			throw new IllegalArgumentException("Property name must start with a selection key.");
		}
	}

	public Object getSelectedBean(String selectionKey)
	{
		return getSubModel(selectionKey).getBean();
	}

	public ValueModel getSelectedBeanChannel(String selectionKey)
	{
		return getSubModel(selectionKey).getBeanChannel();
	}

	public PropertyChangeListener[] getSelectedBeanPropertyChangeListeners(String selectionKey)
	{
		return getSubModel(selectionKey).getBeanPropertyChangeListeners();
	}

	public SelectionModel getSelectionModel()
	{
		return getSelectionModel(DEFAULT_SELECTION);
	}

	public SelectionModel getSelectionModel(String selectionKey)
	{
		SelectionModel selectionModel = getSelectionModels().get(selectionKey);

		if (selectionModel == null)
		{
			selectionModel = new SelectionHolder();
			selectionModel.addSelectionChangeListener(new SelectionChangeHandler(selectionKey));
			getSelectionModels().put(selectionKey, selectionModel);
		}

		return selectionModel;
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
				subModel = presentationModelFactory.createSubModel(this, propertyName);
				getSubModels().put(propertyName, subModel);
			}
		}
		else
		{
			subModel = getSubModels().get(propertyName.substring(0, index));

			if (subModel == null)
			{
				subModel = presentationModelFactory.createSubModel(this, propertyName.substring(0, index));
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
		ValueModel valueModel = null;
		int index = propertyName.lastIndexOf('.');

		if (index != -1)
		{
			valueModel = getSubModel(propertyName.substring(0, index)).getValueModel(propertyName.substring(index + 1, propertyName.length()));
		}
		else
		{
			throw new IllegalArgumentException("Property name must start with a selection key.");
		}

		return valueModel;
	}

	@Override
	public void releaseBeanListeners()
	{
		releaseSelectedBeanListeners(DEFAULT_SELECTION);
	}

	public void releaseSelectedBeanListeners(String selectionKey)
	{
		getSubModel(selectionKey).releaseBeanListeners();
	}

	@Override
	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		removeSelectedBeanPropertyChangeListener(DEFAULT_SELECTION, listener);
	}

	@Override
	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		int index = propertyName.indexOf('.');

		if (index != -1)
		{
			getSubModel(propertyName.substring(0, index)).removeBeanPropertyChangeListener(propertyName.substring(index + 1, propertyName.length()), listener);
		}
		else
		{
			throw new IllegalArgumentException("Property name must start with a selection key.");
		}
	}

	public void removeSelectedBeanPropertyChangeListener(String selectionKey, PropertyChangeListener listener)
	{
		getSubModel(selectionKey).removeBeanPropertyChangeListener(listener);
	}

	@Override
	public void setBean(Object newBean)
	{
		setSelectedBean(DEFAULT_SELECTION, newBean);
	}

	public void setBeanList(List<?> beanList)
	{
		beanListChannel.setValue(beanList);
	}

	public void setSelectedBean(String selectionKey, Object newBean)
	{
		getSubModel(selectionKey).setBean(newBean);
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

	private Map<String, SelectionModel> getSelectionModels()
	{
		if (selectionModels == null)
		{
			selectionModels = new HashMap<String, SelectionModel>();
		}

		return selectionModels;
	}

	private final class SelectionChangeHandler implements PropertyChangeListener
	{
		private String selectionKey;

		public SelectionChangeHandler(String selectionKey)
		{
			setSelectionKey(selectionKey);
		}

		@Override
		@SuppressWarnings("unchecked")
		public void propertyChange(PropertyChangeEvent evt)
		{
			SortedSet<Integer> indexes = (SortedSet<Integer>) evt.getNewValue();

			if (indexes != null && indexes.size() == 1 && getBeanList() != null && getBeanList().size() > indexes.first())
			{
				setSelectedBean(selectionKey, getBeanList().get(indexes.first()));
			}
			else
			{
				setSelectedBean(selectionKey, null);
			}
		}

		public void setSelectionKey(String selectionKey)
		{
			this.selectionKey = Validate.notNull(selectionKey, "Selection key must not be null.");
		}
	}
}

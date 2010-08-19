package com.netappsid.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.netappsid.binding.beans.BeanAdapter;
import com.netappsid.binding.state.State;
import com.netappsid.binding.state.StateModel;
import com.netappsid.binding.state.StatePropertyChangeEvent;

/**
 * 
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.13 $
 */
@SuppressWarnings("serial")
public class DefaultPresentationModel extends PresentationModel
{
	public static final String PROPERTYNAME_BEAN = "bean";

	private final BeanAdapter beanAdapter;
	private final StateModel stateModel;

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
		this.beanAdapter = new BeanAdapter(beanChannel);
		this.stateModel = new StateModel();

		setBeanClass(beanClass);
		beanAdapter.addPropertyChangeListener(BeanAdapter.PROPERTYNAME_BEAN, new BeanChangeHandler());
		beanAdapter.addBeanPropertyChangeListener(new UpdateStateOnBeanPropertyChangeHandler());
	}

	@Override
	public void addBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(listener);
	}

	@Override
	public void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.addBeanPropertyChangeListener(propertyName, listener);
	}

	@Override
	public Object getBean()
	{
		return beanAdapter.getBean();
	}

	@Override
	public ValueModel getBeanChannel()
	{
		return beanAdapter.getBeanChannel();
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners()
	{
		return beanAdapter.getBeanPropertyChangeListeners();
	}

	@Override
	public PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName)
	{
		return beanAdapter.getBeanPropertyChangeListeners(propertyName);
	}

	@Override
	public PresentationModel getSubModel(String modelName)
	{
		if (modelName.contains("."))
		{
			final String propertyName = modelName.substring(0, modelName.indexOf('.'));
			final String subModelName = modelName.substring(modelName.indexOf('.') + 1);

			return getSubModel(propertyName).getSubModel(subModelName);
		}
		else
		{
			if (!getSubModels().containsKey(modelName))
			{
				final PresentationModel subModel = PresentationModelFactory.createPresentationModel(this, modelName);

				getSubModels().put(modelName, subModel);
				stateModel.link(subModel.getStateModel());
			}

			return getSubModels().get(modelName);
		}
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

	@Override
	public void releaseBeanListeners()
	{
		beanAdapter.release();
	}

	@Override
	public void removeBeanPropertyChangeListener(PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(listener);
	}

	@Override
	public void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener)
	{
		beanAdapter.removeBeanPropertyChangeListener(propertyName, listener);
	}

	@Override
	public void setBean(Object newBean)
	{
		beanAdapter.setBean(newBean);
	}

	@Override
	public void setValue(String propertyName, Object newValue)
	{
		getValueModel(propertyName).setValue(newValue);
	}

	@Override
	public StateModel getStateModel()
	{
		return stateModel;
	}

	/**
	 * Responsible for bubbling bean change events to listeners on the PresentationModel.
	 * 
	 * @author Eric Belanger
	 * @author NetAppsID Inc.
	 * @version $Revision: 1.13 $
	 */
	private final class BeanChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			firePropertyChange(PROPERTYNAME_BEAN, evt.getOldValue(), evt.getNewValue(), true);
		}
	}

	private class UpdateStateOnBeanPropertyChangeHandler implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (evt instanceof StatePropertyChangeEvent && ((StatePropertyChangeEvent) evt).isAffectingState())
			{
				stateModel.setState(State.DIRTY);
			}
		}
	}
}

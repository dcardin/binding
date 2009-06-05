package com.netappsid.binding;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.value.ValueModel;
import com.netappsid.validate.Validate;

/**
 * 
 * 
 * @author Eric Belanger
 * @author NetAppsID Inc.
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("serial")
public abstract class PresentationModel extends Model
{
	private Class<?> beanClass;
	private PresentationModel parentModel;
	private Map<String, PresentationModel> subModels;

	public abstract void addBeanPropertyChangeListener(PropertyChangeListener listener);

	public abstract void addBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public abstract Object getBean();

	public abstract ValueModel getBeanChannel();

	public Class<?> getBeanClass()
	{
		return beanClass;
	}

	public abstract PropertyChangeListener[] getBeanPropertyChangeListeners();

	public abstract PropertyChangeListener[] getBeanPropertyChangeListeners(String propertyName);

	public PresentationModel getParentModel()
	{
		return parentModel;
	}

	public PresentationModel getRootModel()
	{
		PresentationModel rootModel = this;

		while (rootModel.getParentModel() != null)
		{
			rootModel = rootModel.getParentModel();
		}

		return rootModel;
	}

	public PresentationModel getSubModel(String propertyName)
	{
		int index = propertyName.indexOf('.');
		PresentationModel model = null;

		if (index != -1)
		{
			String subPropertyName = propertyName.substring(index + 1, propertyName.length());
			
			propertyName = propertyName.substring(0, index);
			model = getSubModel(propertyName).getSubModel(subPropertyName);
		}
		
		model = getSubModels().get(propertyName);
		
		if (model == null)
		{
			model = PresentationModelFactory.createPresentationModel(this, propertyName);
			model.setParentModel(this);
			getSubModels().put(propertyName, model);
		}

		return model;
	}

	public abstract Object getValue(String propertyName);

	public abstract ValueModel getValueModel(String propertyName);

	public abstract ValueModel getValueModel(String propertyName, String getterName, String setterName);

	public abstract void releaseBeanListeners();

	public abstract void removeBeanPropertyChangeListener(PropertyChangeListener listener);

	public abstract void removeBeanPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	public abstract void setBean(Object newBean);

	public abstract void setValue(String propertyName, Object newValue);

	protected Map<String, PresentationModel> getSubModels()
	{
		if (subModels == null)
		{
			subModels = new HashMap<String, PresentationModel>();
		}

		return subModels;
	}
	
	protected void setBeanClass(Class<?> beanClass)
	{
		this.beanClass = Validate.notNull(beanClass, "Bean class must not be null.");
	}
	
	protected void setParentModel(PresentationModel parentModel)
	{
		this.parentModel = parentModel;
	}
}

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
 * @version $Revision: 1.2 $
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

	public abstract PresentationModel getSubModel(String propertyName);

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

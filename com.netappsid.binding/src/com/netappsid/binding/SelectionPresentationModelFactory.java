package com.netappsid.binding;

import java.util.List;

import com.google.inject.Inject;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;

public class SelectionPresentationModelFactory
{
	private final ChangeSupportFactory changeSupportFactory;
	
	@Inject
	public SelectionPresentationModelFactory(ChangeSupportFactory changeSupportFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
	}
	
	public SelectionPresentationModel create(Class<?> beanClass)
	{
		return new SelectionPresentationModel(changeSupportFactory, beanClass, new ValueHolder(null, true));
	}
	
	public SelectionPresentationModel create(Class<?> beanClass, List<?> beanList)
	{
		return new SelectionPresentationModel(changeSupportFactory, beanClass, new ValueHolder(beanList, true));
	}
	
	public SelectionPresentationModel create(Class<?> beanClass, ValueModel beanListChannel)
	{
		return new SelectionPresentationModel(changeSupportFactory, beanClass, beanListChannel);
	}
}

package com.netappsid.binding;

import java.util.Map;

import com.google.inject.Inject;
import com.netappsid.binding.beans.support.ChangeSupportFactory;
import com.netappsid.binding.value.ValueHolder;
import com.netappsid.binding.value.ValueModel;
import com.netappsid.validate.Validate;

public class DynamicPresentationModelFactory
{
	private ChangeSupportFactory changeSupportFactory;
	
	@Inject
	public DynamicPresentationModelFactory(ChangeSupportFactory changeSupportFactory)
	{
		this.changeSupportFactory = changeSupportFactory;
	}
	
	public DynamicPresentationModel create()
	{
		return new DynamicPresentationModel(changeSupportFactory, new ValueHolder());
	}
	
	public DynamicPresentationModel create(Map<String, ?> map)
	{
		return new DynamicPresentationModel(changeSupportFactory, new ValueHolder(map));
	}
	
	public DynamicPresentationModel create(ValueModel mapChannel)
	{
		return new DynamicPresentationModel(changeSupportFactory, Validate.notNull(mapChannel, "Map Channel cannot be null."));
	}
}

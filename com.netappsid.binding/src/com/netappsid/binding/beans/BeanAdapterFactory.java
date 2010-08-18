package com.netappsid.binding.beans;

import com.netappsid.binding.value.ValueModel;

public interface BeanAdapterFactory
{
	BeanAdapter create();
	
	BeanAdapter create(Object bean);
	
	BeanAdapter create(ValueModel beanChannel);
}

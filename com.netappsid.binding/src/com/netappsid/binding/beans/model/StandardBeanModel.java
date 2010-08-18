package com.netappsid.binding.beans.model;

import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StandardBeanModel extends BeanModel
{
	public StandardBeanModel()
	{
		super(new StandardChangeSupportFactory());
	}
}

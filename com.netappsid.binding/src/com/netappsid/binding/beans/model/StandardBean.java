package com.netappsid.binding.beans.model;

import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StandardBean extends Bean
{
	public StandardBean()
	{
		super(new StandardChangeSupportFactory());
	}
}

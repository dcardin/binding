package com.netappsid.binding.beans;

import com.netappsid.binding.beans.support.StandardChangeSupportFactory;

public class StandardBean extends Bean
{
	public StandardBean()
	{
		super(new StandardChangeSupportFactory());
	}
}

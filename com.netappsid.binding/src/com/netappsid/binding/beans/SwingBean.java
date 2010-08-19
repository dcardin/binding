package com.netappsid.binding.beans;

import com.netappsid.binding.beans.support.SwingChangeSupportFactory;

public class SwingBean extends Bean
{
	public SwingBean()
	{
		super(new SwingChangeSupportFactory());
	}
}

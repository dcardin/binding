package com.netappsid.test.beans;

import com.netappsid.binding.beans.BeanModel;

public class TestBeanDetail extends BeanModel
{
	private final String identifier;
	private String property;
	private TestBeanDetail bean;
	
	public TestBeanDetail(String identifier)
	{
		this.identifier = identifier;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	public String getProperty()
	{
		return property;
	}

	public void setProperty(String property)
	{
		final String oldValue = this.property;
		
		this.property = property;
		firePropertyChange(PROPERTYNAME_PROPERTY, oldValue, property);
	}
	
	public TestBeanDetail getBean()
	{
		return bean;
	}

	public void setBean1(TestBeanDetail bean)
	{
		final TestBeanDetail oldValue = this.bean;
		
		this.bean = bean;
		firePropertyChange(PROPERTYNAME_BEAN, oldValue, bean);
	}
	
	public static final String PROPERTYNAME_PROPERTY = "property";
	public static final String PROPERTYNAME_BEAN = "bean";
}
